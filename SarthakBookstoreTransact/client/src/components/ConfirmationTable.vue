<template>
  <div class="confirmation-table-body">
    <div>
      <table class="items">
        <tr class="heading">
          <td>Title</td>
          <td>Quantity</td>
          <td>Price</td>
        </tr>
        <tr
          v-for="(item, index) in this.$store.state.orderDetails.lineItems"
          :key="item.productId"
        >
          <td>
            {{ $store.state.orderDetails.books[index].title }}
          </td>
          <td>{{ item.quantity }}</td>
          <td>
            {{
              (item.quantity * $store.state.orderDetails.books[index].price)
                | asDollarsAndCents
            }}
          </td>
        </tr>
      </table>
    </div>
    <div class="amount-details">
      <div class="bold" width="15em">
        <table>
          <tr>
            <td>Subtotal:</td>
            <td class="price">
              {{
                ($store.state.orderDetails.order.amount -
                  $store.state.surCharge)
                  | asDollarsAndCents
              }}
            </td>
          </tr>
          <tr>
            <td>SurCharge:</td>
            <td class="price">
              {{ $store.state.surCharge | asDollarsAndCents }}
            </td>
          </tr>
          <tr class="total">
            <td>Total:</td>
            <td class="price">
              {{ $store.state.orderDetails.order.amount | asDollarsAndCents }}
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ConfirmationTable",
};
</script>

<style scoped>
div.confirmation-table-body {
  justify-content: center;
  padding: 1em;
}

.amount-details {
  text-align: right;
  padding: 0 2.5em;
}
table.items {
  border: 3px var(--primary-color) solid;
  border-radius: 10px;
  width: fit-content;
  margin-top: 1em;
  margin-bottom: 1em;
}

td {
  display: table-cell;
  padding: 0.5em 0.5em;
  background-color: white;
  vertical-align: middle;
}

.heading > td {
  background-color: var(--primary-color);
  color: white;
}

td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}

div.bold {
  border: 3px solid var(--primary-color);
  border-radius: 1em;
  background-color: white;
  padding: 0.5em;
  width: 230px;
}

td {
  padding: 0.25em 1em 0.25em 1em;
}

td.price {
  text-align: right;
  padding-left: 2em;
  padding-right: 8px;
  margin-right: 0.5em;
}
tr.total > td {
  border: 1px solid;
  background-color: var(--primary-color-dark);
  border-radius: 0px 5px 0px 5px;
  color: white;
  border-left: 0px;
  border-right: 0px;
}
</style>
