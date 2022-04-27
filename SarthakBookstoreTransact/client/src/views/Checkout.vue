<template>
  <div
    class="checkout-page-continue"
    v-if="$store.state.cart.numberOfItems === 0"
  >
    <p>Your Cart is Empty. You need to add an item to your cart to checkout.</p>
    <router-link :to="'/category/' + $store.state.selectedCategoryName"
      ><button class="button continue">
        Add items to your Cart
      </button></router-link
    >
  </div>
  <div @submit.prevent="submitOrder" class="checkout-page" v-else>
    <section class="checkout-page-body" v-if="!cart.empty">
      <form>
        <div class="column">
          <div>
            <label for="name">Name</label>
            <input
              type="text"
              size="25"
              id="name"
              name="name"
              v-model.lazy="$v.name.$model"
            />
          </div>
          <template v-if="$v.name.$error">
            <span class="error" v-if="!$v.name.required">Name is required</span>
            <span class="error" v-else-if="!$v.name.minLength">
              Name must have at least
              {{ $v.name.$params.minLength.min }} letters.
            </span>
            <span class="error" v-else-if="!$v.name.maxLength">
              Name can have at most
              {{ $v.name.$params.maxLength.max }} letters.
            </span>
            <span class="error" v-else> An unexpected error occurred. </span>
          </template>

          <div>
            <label for="Address">Address</label>
            <input
              type="text"
              size="25"
              id="address"
              name="address"
              v-model.lazy="$v.address.$model"
            />
          </div>
          <template v-if="$v.address.$error">
            <span class="error" v-if="!$v.address.required"
              >Address is required</span
            >
            <span class="error" v-else-if="!$v.address.minLength">
              Address must have at least
              {{ $v.address.$params.minLength.min }} letters.
            </span>
            <span class="error" v-else-if="!$v.address.maxLength">
              Address can have at most
              {{ $v.address.$params.maxLength.max }} letters.
            </span>
            <span class="error" v-else> An unexpected error occurred. </span>
          </template>

          <div>
            <label for="phone">Phone</label>
            <input
              class="textField"
              type="text"
              size="25"
              id="phone"
              name="phone"
              v-model.lazy="$v.phone.$model"
            />
          </div>
          <template v-if="$v.phone.$error">
            <span class="error" v-if="!$v.phone.required"
              >Phone number is required.</span
            >
            <span class="error" v-else-if="!$v.phone.phoneValidator"
              >Phone number is invalid.</span
            >
            <span class="error" v-else>An unexpected error occured.</span>
          </template>

          <div>
            <label for="email">Email</label>
            <input
              type="text"
              size="25"
              id="email"
              name="email"
              v-model.lazy="$v.email.$model"
            />
          </div>
          <template v-if="$v.email.$error">
            <span class="error" v-if="!$v.email.required"
              >Email number is required.</span
            >
            <span class="error" v-else-if="!$v.email.emailValidator"
              >Email number is invalid.</span
            >
            <span class="error" v-else>An unexpected error occured.</span>
          </template>

          <div>
            <label for="ccNumber">Credit card</label>
            <input
              type="text"
              autocomplete="ccNumber"
              size="25"
              id="ccNumber"
              name="ccNumber"
              class="form-control"
              v-model.lazy="$v.ccNumber.$model"
            />
          </div>
          <template v-if="$v.ccNumber.$error">
            <span class="error" v-if="!$v.ccNumber.required"
              >Credit Card number is required.</span
            >
            <span class="error" v-else-if="!$v.ccNumber.creditCard"
              >Credit Card number is invalid.</span
            >
            <span class="error" v-else>An unexpected error occured.</span>
          </template>

          <div>
            <label>Exp Date </label>
            <select class="month" v-model="ccExpiryMonth">
              <option
                v-for="(month, index) in months"
                :key="index"
                :value="index + 1"
              >
                {{ month }} ({{ index + 1 }})
              </option>
            </select>
            <select class="year" width="10">
              <option v-for="(year, index) in 15" :key="index">
                {{ yearFrom(index) }}
              </option>
            </select>
          </div>
        </div>

        <div class="info">
          <div class="bold" width="15em">
            <table>
              <tr>
                <td>Subtotal:</td>
                <td class="price">{{ cart.subtotal | asDollarsAndCents }}</td>
              </tr>
              <tr>
                <td>SurCharge:</td>
                <td class="price">{{ cart.surcharge | asDollarsAndCents }}</td>
              </tr>
              <tr>
                <td>Total:</td>
                <td class="price">
                  {{ (cart.subtotal + cart.surcharge) | asDollarsAndCents }}
                </td>
              </tr>
            </table>
          </div>
          <input
            type="submit"
            name="submit"
            class="button"
            :disabled="checkoutStatus == 'PENDING'"
            value="Complete Purchase"
          />
          <section v-show="checkoutStatus != ''" class="checkoutStatusBox">
            <div class="submit-error" v-if="checkoutStatus == 'ERROR'">
              Error: Please fix the problems in the fields with error message in
              red.
            </div>

            <div v-else-if="checkoutStatus == 'PENDING'">Processing...</div>

            <div v-else-if="checkoutStatus == 'OK'">Order placed...</div>

            <div v-else>
              <!-- "checkoutStatus == 'SERVER_ERROR'" -->
              An unexpected error occurred, please try again.
            </div>
          </section>
        </div>
      </form>
    </section>
  </div>
</template>

<script>
import {
  required,
  // email,
  minLength,
  maxLength,
} from "vuelidate/lib/validators";

import isCreditCard from "validator/lib/isCreditCard";
import isMobilePhone from "validator/lib/isMobilePhone";
import isEmail from "validator/lib/isEmail";

const emailValidator = (value) => isEmail(value);

const phoneValidator = (value) => isMobilePhone(value, "en-US");
const creditCard = (value) => isCreditCard(value);

export default {
  data() {
    return {
      name: "",
      address: "",
      phone: "",
      email: "",
      ccNumber: "",
      ccExpiryMonth: new Date().getMonth() + 1,
      ccExpiryYear: new Date().getFullYear(),
      checkoutStatus: "",
    };
  },
  validations: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    phone: {
      required,
      phoneValidator,
    },
    email: {
      required,
      emailValidator,
    },
    address: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    ccNumber: {
      required,
      creditCard,
    },
  },

  computed: {
    cart() {
      return this.$store.state.cart;
    },
    months() {
      return [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
    },
  },

  methods: {
    submitOrder() {
      console.log("Submit order");
      this.$v.$touch(); // Ensures validators always run
      if (this.$v.$error) {
        this.checkoutStatus = "ERROR";
      } else {
        this.checkoutStatus = "PENDING";
        setTimeout(() => {
          this.$store
            .dispatch("placeOrder", {
              name: this.name,
              address: this.address,
              phone: this.phone,
              email: this.email,
              ccNumber: this.ccNumber,
              ccExpiryMonth: this.ccExpiryMonth,
              ccExpiryYear: this.ccExpiryYear,
            })
            .then(() => {
              this.checkoutStatus = "OK";
              this.$router.push({ name: "confirmation" });
            })
            .catch((reason) => {
              this.checkoutStatus = "SERVER_ERROR";
              console.log("Error placing order", reason);
            });
        }, 1000);
      }
    },

    /* NOTE: For example yearFrom(0) == 2021 */
    yearFrom(index) {
      return new Date().getFullYear() + index;
    },
  },
};
</script>

<style scoped>
.checkout-page {
  background: oldlace;
  color: var(--primary-color-dark);
  margin-left: 2em;
  margin-right: 2em;
  border-radius: 1em;
  padding-bottom: 0.5em;
}
.checkout-page-body {
  display: flex;
  justify-content: center;
  padding: 1em;
}

form {
  display: flex;
}

form > div.column {
  display: flex;
  flex-direction: column;
}

form > div.column > div {
  display: flex;
  justify-content: space-between;
  padding: 0em 1em;
  margin-top: 0.5em;
  text-align: left;
}

.error {
  font-size: small;
  text-align: right;
  margin-right: 1rem;
  padding-bottom: 0.5em;
}
div.info {
  padding: 0.5em 0.5em 0.5em 2rem;
}

div.bold {
  border: solid var(--primary-color);
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
}

form > div.column > div > input,
form > div.column > div > select {
  background-color: white;
  border: solid var(--primary-color-dark);
  border-radius: 05em;
  margin-left: 0.5em;
  text-align: center;
}

div.info > div {
  width: 230px;
}

input.button {
  text-align: center;
  margin-top: 1em;
  justify-content: center;
  width: 230px;

  background: var(--primary-color);
  color: white;
  border: none;
  border-radius: 90px;
  cursor: pointer;
}
input.button:hover:enabled {
  background-color: var(--primary-color-dark);
}

input.button:disabled {
  background-color: var(--primary-color-dark);
  cursor: default;
}

form > div.column > div > select {
  text-align: center;
}
form > div.column > div > select.month {
  text-align: center;
  width: 8.5em;
  margin-left: 1em;
}
form > div > select.year {
  text-align: center;
  width: 5em;
  margin-left: 0px;
  padding-left: 0.25em;
}

select:active {
  border-color: #8e0ba4;
}

form > div.column > .error {
  color: red;
  text-align: right;
}

.submit-error {
  color: red;
  text-align: justify;
  font-size: small;
}

label {
  text-align: left;
}

.checkoutStatusBox {
  margin-top: 0.5em;
  width: 230px;
  text-align: center;
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

.checkout-page-continue {
  margin-top: 5em;
  background: oldlace;
  color: var(--primary-color-dark);
  text-align: center;
  margin-left: 2em;
  margin-right: 2em;
  border-radius: 1em;
  padding: 6em;
}
</style>
