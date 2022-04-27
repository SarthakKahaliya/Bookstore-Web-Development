package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;
import business.order.Order;
import business.order.OrderDao;
import business.order.LineItem;
import business.order.LineItemDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	private LineItemDao lineItemDao;

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException | ParseException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}

	private Date getDate(String monthString, String yearString) throws ParseException {

		String date_string = monthString+"-"+yearString;
		SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
		Date new_date = formatter.parse(date_string);
		return new_date;
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		Random random = new Random();
		return random.nextInt(999999999);
	}

	private void validateCustomer(CustomerForm customerForm) {
		try {
			String name = customerForm.getName();
			if (name == null || name.equals("") || name.length() < 4 || name.length() > 45) {
				throw new ApiException.InvalidParameter("Invalid name field");
			}
		}
		catch (Exception e){
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		try {
			String address = customerForm.getAddress();
			if (address == null || address.equals("") || address.length() < 4 || address.length() > 45) {
				throw new ApiException.InvalidParameter("Invalid address field");
			}
		}
		catch (Exception e){
			throw new ApiException.InvalidParameter("Invalid address field");
		}

		try {
			String phone = customerForm.getPhone();
			phone = phone.replace(" ", "").replace("+1", "");
			phone = phone.replace("-", "").replace("(", "").replace(")", "");
			if (phone == null || phone.equals("") || phone.length() != 10) {
				throw new ApiException.InvalidParameter("Invalid Phone field");
			}
		}
		catch (Exception e){
			throw new ApiException.InvalidParameter("Invalid Phone field");
		}

		try {
			String email = customerForm.getEmail();
			if (email == null || email.equals("") || email.contains(" ") || !email.contains("@") || email.endsWith(".")) {
				throw new ApiException.InvalidParameter("Invalid email field");
			}
		}
		catch (Exception e){
			throw new ApiException.InvalidParameter("Invalid email field");
		}

		try {
			String ccNumber = customerForm.getCcNumber();
			ccNumber = ccNumber.replace(" ", "");
			ccNumber = ccNumber.replaceAll("-", "");
			System.out.println(ccNumber);
			if (ccNumber == null || ccNumber.equals("") || ccNumber.length() < 14 || ccNumber.length() > 16) {
				throw new ApiException.InvalidParameter("Invalid Credit Card field");
			}
		}
		catch (Exception e){
			throw new ApiException.InvalidParameter("Invalid Credit Card field");
		}

		try {
			if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
				throw new ApiException.InvalidParameter("Invalid expiry date");
			}
		}
		catch (Exception e){
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		YearMonth current = YearMonth.now();
		int year = Integer.parseInt(ccExpiryYear);
		int month = Integer.parseInt(ccExpiryMonth);
		int currYear = current.get(ChronoField.YEAR);
		int currMonth = current.get(ChronoField.MONTH_OF_YEAR);
		if(currYear > year || (currMonth > month && currYear == year) || month > 12 || month < 1) {
			return true;
		}
		return false;
	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());

			if(item.getPrice() != databaseBook.getPrice()) {
				throw new ApiException.InvalidParameter("Invalid price");
			}
			if(item.getCategoryid() != databaseBook.getCategoryId()) {
				throw new ApiException.InvalidParameter("Invalid Book Category");
			}
		});
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

}
