import Vue from "vue";
import Vuex from "vuex";
import ApiService from "@/services/ApiService";
import { ShoppingCart } from "@/models/ShoppingCart";

Vue.use(Vuex);
export const CART_STORAGE_KEY = "";
export const ORDER_STORAGE_KEY = "orderDetails";
export default new Vuex.Store({
  state: {
    categories: [],
    selectedCategoryName: "",
    selectedCategoryBooks: [],
    cart: new ShoppingCart(),
    orderDetails: null,
    surCharge: 500,
  },
  mutations: {
    SET_CATEGORIES(state, newCategories) {
      state.categories = newCategories;
    },
    ADD_TO_CART(state, book) {
      state.cart.addItem(book, 1);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SELECT_CATEGORY(state, categoryName) {
      state.selectedCategoryName = categoryName;
    },
    SET_SELECT_CATEGORY_BOOKS(state, book) {
      state.selectedCategoryBooks = book;
    },
    UPDATE_CART(state, { book, quantity }) {
      state.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_CART(state, shoppingCart) {
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(shoppingCart));
      let newCart = new ShoppingCart();
      shoppingCart.items.forEach((item) => {
        newCart.addItem(item.book, item.quantity);
      });
      state.cart = newCart;
    },
    CLEAR_CART(state) {
      state.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },

    SET_ORDER_DETAILS(state, orderDetails) {
      state.orderDetails = orderDetails;
      sessionStorage.setItem(
        ORDER_STORAGE_KEY,
        JSON.stringify(this.state.orderDetails)
      );
    },
    CLEAR_ORDER_DETAILS(state) {
      sessionStorage.removeItem(ORDER_STORAGE_KEY);
      state.orderDetails = null;
    },
  },
  actions: {
    fetchCategories(context) {
      ApiService.fetchCategories()
        .then((categories) => {
          console.log("Data: ", categories);
          context.commit("SET_CATEGORIES", categories);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
        });
    },
    addToCart(context, book) {
      context.commit("ADD_TO_CART", book);
    },
    selectCategory(context, name) {
      context.commit("SELECT_CATEGORY", name);
    },
    fetchSelectedCategoryBooks(context, name) {
      return ApiService.fetchSeletedCategoryBooks(name)
        .then((selectedCategoryBooks) => {
          console.log("Data: ", selectedCategoryBooks);
          context.commit("SET_SELECT_CATEGORY_BOOKS", selectedCategoryBooks);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
          throw reason;
        });
    },
    updateCart(context, { book, quantity }) {
      context.commit("UPDATE_CART", { book, quantity });
    },
    clearCart(context) {
      context.commit("CLEAR_CART");
    },
    placeOrder({ commit, state }, customerForm) {
      return ApiService.placeOrder({
        cart: state.cart,
        customerForm: customerForm,
      }).then((orderDetails) => {
        commit("SET_ORDER_DETAILS", orderDetails);
        commit("CLEAR_CART");
      });
    },
  },
});
