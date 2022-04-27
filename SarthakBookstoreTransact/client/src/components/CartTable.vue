<template>
  <div class="cart-table">
    <ul>
      <li class="cart-heading">
        <div class="nothing"></div>
        <div class="cart-heading-book">Book</div>
        <div class="cart-heading-price">Price</div>
        <div class="cart-heading-quantity">Quantity</div>
        <div class="cart-heading-subtotal">Amount</div>
      </li>
      <li v-for="item in $store.state.cart.items" :key="item.book.bookID">
        <div
          class="button clear"
          @click="updateCart(item.book, (item.quantity = 0))"
        >
          Clear
        </div>
        <div class="cart-book-image">
          <img
            v-bind:src="
              require('@/assets/images/books/' + bookImageFileName(item.book))
            "
            :alt="item.book.title"
          />
        </div>
        <div class="cart-book-title">{{ item.book.title }}</div>
        <div class="cart-book-price">
          {{ item.book.price | asDollarsAndCents }}
        </div>
        <div class="cart-book-quantity">
          <button
            class="icon-button dec-button"
            @click="updateCart(item.book, quantityCheck(item.quantity - 1))"
          >
            <i class="fas fa-minus-circle"></i>
          </button>
          <span class="row-book-quantity"> {{ item.quantity }}</span
          ><button
            class="icon-button inc-button"
            @click="updateCart(item.book, quantityCheck(item.quantity + 1))"
          >
            <i class="fas fa-plus-circle"></i>
          </button>
        </div>
        <div class="subtotal">
          {{ (item.quantity * item.book.price) | asDollarsAndCents }}
        </div>
        <div class="row-sep"></div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "CartTable",

  methods: {
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      name = name.replace(/\n/g, "");
      console.log(name);
      return `${name}.jpg`;
    },
    updateCart(book, quantity) {
      this.$store.dispatch("updateCart", { book, quantity });
    },
    quantityCheck(quantity) {
      if (quantity <= 99) {
        return quantity;
      }
      if (quantity > 99) {
        return 99;
      }
    },
  },
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content max-content minmax(10em, 20em) max-content max-content max-content;
  row-gap: 1em;
  padding: 1em;
  background: oldlace;
  justify-content: center;
  margin: 0 auto;
  border-radius: 2em;
}
img {
  aspect-ratio: 127/180;
  width: 100px;
}
ul,
li {
  display: contents;
}

.row-sep {
  grid-column: 1 / -1;
  background-color: gray;
  height: 2px;
}

.cart-heading {
  background-color: black;
  color: white;
}
.cart-heading > * {
  background-color: black;
}

.cart-heading-book {
  grid-column: 2/4;
  padding: 0 1.25em;
}

.cart-heading-price {
  grid-column: 4/5;
  padding: 0 1em;
}

.cart-heading-quantity {
  grid-column: 5/6;
  padding: 0 1em;
}

.cart-heading-subtotal {
  grid-column: -2/-1;
  padding: 0 1em;
}

.cart-book-image {
  padding: 0 1em;
}
.cart-book-title {
  padding: 0 1em;
}
.cart-book-price {
  padding: 0 1em;
}
.cart-book-quantity {
  padding: 0 1em;
}
.subtotal {
  padding: 0 1em;
}

.icon-button {
  color: #8e0ba4;
  font-size: large;
  border: none;
  background: transparent;
  cursor: pointer;
}

.icon-button:hover {
  color: #560364;
}

.button.clear {
  background-color: transparent;
  color: red;
}
</style>
