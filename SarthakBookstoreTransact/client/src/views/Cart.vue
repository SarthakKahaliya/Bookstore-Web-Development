<template>
  <div class="cart-page">
    <section
      v-if="$store.state.cart.numberOfItems === 0"
      class="empty-cart-page"
    >
      <p>
        Your Cart is Empty. You need to add an item to your cart to checkout.
      </p>
      <router-link :to="'/category/' + $store.state.selectedCategoryName"
        ><button class="button continue-empty">
          Add items to your Cart
        </button></router-link
      >
    </section>
    <section v-else class="non-empty-cart-page">
      <div class="cart-buttons">
        <p v-if="$store.state.cart.numberOfItems === 1">
          This Cart has : {{ $store.state.cart.numberOfItems }} book.
        </p>
        <p v-else>
          This Cart has : {{ $store.state.cart.numberOfItems }} books.
        </p>
        <button class="button" @click="clearCart()">Clear Cart</button>
      </div>
      <div><cart-table></cart-table></div>
      <div class="subtotal">
        Subtotal: {{ $store.state.cart.subtotal | asDollarsAndCents }}
      </div>
      <div class="cart-buttons">
        <router-link :to="'/category/' + $store.state.selectedCategoryName"
          ><button class="button continue">
            Continue Shopping
          </button></router-link
        >
        <router-link to="../checkout" tag="button" class="button proceed"
          >Proceed to Checkout</router-link
        >
      </div>
    </section>
  </div>
</template>

<script>
import CartTable from "@/components/CartTable";
export default {
  name: "Cart",
  components: { CartTable },
  methods: {
    clearCart() {
      this.$store.dispatch("clearCart");
    },
  },
};
</script>

<style scoped>
.subtotal {
  text-align: right;
  margin-right: 8em;
}

p {
  margin-left: 2em;
}

.button {
  text-align: center;
  justify-content: center;
  border: none;
  cursor: pointer;
  margin-right: 2em;
}

.button.proceed {
  text-align: center;
  margin-right: 1em;
  justify-content: center;
  width: 216px;
  height: 60px;
  background: var(--primary-color);
  color: white;
  border: none;
  border-radius: 90px;
  cursor: pointer;
}

.button.continue {
  text-align: center;
  margin-left: 1em;
  justify-content: center;
  width: 216px;
  height: 60px;
  background: white;
  color: var(--primary-color);
  border: solid var(--primary-color);
  border-radius: 90px;
  cursor: pointer;
}

.button.proceed:hover,
.button.proceed:active {
  background: var(--primary-color-dark);
}

.button.continue:hover {
  background-color: var(--primary-color-dark);
  color: white;
  border-color: var(--primary-color-dark);
}

.non-empty-cart-page {
  display: flex;
  flex-direction: column;
  gap: 1em;
}
.cart-page {
  padding: 1em;
}
.cart-buttons {
  display: flex;
  justify-content: space-between;
}

.button.continue-empty {
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

.button.continue-empty:hover {
  background-color: var(--primary-color-dark);
  color: white;
  border-color: var(--primary-color-dark);
}

.empty-cart-page {
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
