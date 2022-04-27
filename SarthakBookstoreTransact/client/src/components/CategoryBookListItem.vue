<template>
  <li class="book-box">
    <div>
      <router-link to="/" v-if="book.isPublic">
        <i class="fas fa-book-reader bookIcon"></i>
      </router-link>

      <router-link to="../category/coding">
        <div class="book-image">
          <img
            v-bind:src="
              require('@/assets/images/books/' + bookImageFileName(book))
            "
            :alt="book.title"
          />
        </div>
      </router-link>
    </div>

    <div class="book-title">{{ book.title }}</div>
    <div class="book-author">{{ book.author }}</div>
    <div class="book-price">{{ book.price | asDollarsAndCents }}</div>
    <button class="add-to-cart" @click="addToCart(book)">Add to Cart</button>
  </li>
</template>

<script>
export default {
  name: "categoryBookListItem",
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  methods: {
    addToCart(book) {
      this.$store.dispatch("addToCart", book);
      //this.$store.state.cart.addItem(book, 1)
    },
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      name = name.replace(/\n/g, "");
      console.log(name);
      return `${name}.jpg`;
    },
  },
};
</script>
<style scoped>
li.book-box {
  display: grid;

  align-content: center;
  text-align: center;
  justify-content: center;
  justify-items: center;
}

.book-box {
  display: flex;
  flex-direction: column;
  background: #fdf5e6;
  border: solid var(--primary-color-dark);
  border-radius: 10px 10px 10px 10px;
  padding: 1em;
  gap: 0.25em;
  align-content: center;
  text-align: center;
  justify-content: center;
  justify-items: center;
}

.book-title {
  font-weight: bold;
}

.book-author {
  font-style: italic;
}

.add-to-cart {
  color: white;
  background: var(--primary-color);
  border: solid var(--primary-color);
  border-radius: 5px 5px 5px 5px;
  width: 132px;
}

.add-to-cart:hover {
  background: var(--primary-color-dark);
  border: solid var(--primary-color-dark);
  color: white;
  cursor: pointer;
}

.fas.fa-book-reader.bookIcon {
  border: solid var(--primary-color);
  background: var(--primary-color);
  color: #fdf5e6;
  border-radius: 5px;

  margin-top: 2px;
  width: 40px;
  cursor: pointer;
}

.fas.fa-book-reader.bookIcon:hover {
  border: solid var(--primary-color-dark);
  background: var(--primary-color-dark);
  width: 45px;
}

.bookIcon {
  position: absolute;
  font-size: 30px;
  background: white;
  color: var(--primary-color);
  border: solid var(--primary-color);
  border-radius: 10px;
  margin-left: 40px;
  margin-top: 150px;
}

.bookIcon:hover {
  position: absolute;
  font-size: 35px;
  background: white;
  color: var(--primary-color);
  border: solid var(--primary-color);
  border-radius: 10px;
  margin-left: 35px;
  margin-top: 150px;
}

img {
  aspect-ratio: 127/180;
  width: 127px;
}
</style>
