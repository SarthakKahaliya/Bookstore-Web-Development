<template>
  <div class="confirmationView">
    <section
      class="confirmation-page-continue"
      v-if="!$store.state.orderDetails"
    >
      <p>You have not placed any order yet.</p>
      <router-link :to="'/category/' + $store.state.selectedCategoryName"
        ><button class="button continue">Continue Shopping</button></router-link
      >
    </section>
    <section v-else>
      <ul>
        <li>
          Confirmation #:
          {{ $store.state.orderDetails.order.confirmationNumber }}
        </li>
        <li>
          Time: {{ getdate($store.state.orderDetails.order.dateCreated, -5) }}
        </li>
      </ul>
      <confirmation-table> </confirmation-table>
      <ul class="details">
        <li>{{ $store.state.orderDetails.customer.customerName }}</li>
        <li>{{ $store.state.orderDetails.customer.address }}</li>
        <li>{{ $store.state.orderDetails.customer.email }}</li>
        <li>{{ $store.state.orderDetails.customer.phone }}</li>
        <li>
          **** **** ****
          {{ ccNum($store.state.orderDetails.customer.ccNumber) }} ({{
            ccExpDate($store.state.orderDetails.customer.ccExpDate, -5)
          }})
        </li>
      </ul>
      <div id="customerInfo"></div>
    </section>
  </div>
</template>

<script>
import ConfirmationTable from "@/components/ConfirmationTable";
export default {
  name: "Confirmation",
  components: { ConfirmationTable },
  methods: {
    ccNum(x) {
      return x.substring(x.length - 4);
    },
    ccExpDate(day, offset) {
      const d = new Date(day);
      const utc = d.getTime() + d.getTimezoneOffset() * 60000;
      const nd = new Date(utc + 3600000 * offset);
      let month = nd.getMonth();
      month += 1;
      if (month < 10) {
        month = "0" + month;
      }
      const year = nd.getFullYear();
      return month + "-" + year;
    },

    getdate(day, offset) {
      const d = new Date(day);
      const utc = d.getTime() + d.getTimezoneOffset() * 60000;
      const nd = new Date(utc + 3600000 * offset);
      const dte = nd.toLocaleDateString();
      let min = nd.getMinutes();
      if (min < 10) {
        min = "0" + min;
      }
      let hrs = nd.getHours();
      if (hrs < 10) {
        hrs = "0" + hrs;
      }
      return dte + " " + hrs + ":" + min;
    },
  },
};
</script>

<style scoped>
section {
  background-color: oldlace;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  align-content: center;
  margin: 2em;
  padding: 2em;
}

ul {
  text-align: left;
}
ul > li {
  margin: 0.25em;
}
.button.continue {
  text-align: center;
  margin-left: 1em;
  margin-top: 1em;
  justify-content: center;
  width: 216px;
  height: 60px;
  background: white;
  color: var(--primary-color);
  border: solid var(--primary-color);
  border-radius: 90px;
  cursor: pointer;
  justify-content: center;
  text-align: center;
  justify-items: center;
}

.button.continue:hover {
  background-color: var(--primary-color-dark);
  color: white;
  border-color: var(--primary-color-dark);
}

.confirmation-page-continue {
  margin-top: 5em;
  background: oldlace;
  color: var(--primary-color-dark);
  text-align: center;
  margin-left: 2em;
  margin-right: 2em;
  border-radius: 1em;
  padding: 6em;
}

li {
  font-size: large;
}

ul.details {
  padding: 0.25em;
  border: 3px var(--primary-color) solid;
  border-radius: 10px;
  color: var(--primary-color-dark);
  background-color: white;
}
</style>
