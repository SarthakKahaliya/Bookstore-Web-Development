<template>
  <header class="index-body-container">
    <section class="bookstore-logo-and-title">
      <router-link to="/" class="logo">
        <img
          src="@/assets/images/site/bookstore-logo.png"
          alt="Book Buggz Logo"
        />
      </router-link>
      <router-link class="text-logo" to="/">
        <h1 class="title-text">Book Buggz</h1>
      </router-link>
    </section>
    <section class="section-search-bar">
      <form action="category.html">
        <div class="searchContainer">
          <input
            class="searchBox"
            type="search"
            name="search"
            placeholder="Search Book..."
          />
          <button
            type="submit"
            value="Search"
            class="button searchButton fas fa-search icon"
          />
        </div>
      </form>
    </section>
    <section class="header-dropdown-and-cart">
      <div>
        <router-link class="button-cart-number" to="/cart">
          <div class="button fas fa-shopping-cart"></div>
          <div>{{ $store.state.cart.numberOfItems }}</div>
        </router-link>
      </div>
      <button class="button login">login</button>
      <header-dropdown-menu></header-dropdown-menu>
    </section>
  </header>
</template>

<script>
import HeaderDropdownMenu from "@/components/HeaderDropdown";
export default {
  name: "AppHeader",
  components: { HeaderDropdownMenu },
  component: {
    HeaderDropdownMenu,
  },
};
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  flex-wrap: wrap;
  grid-template-columns: 1fr 1fr 1fr;
  justify-content: space-between;
  padding: 0.5em;
  gap: 1em;
}

.bookstore-logo-and-title {
  padding: 1em;
  display: flex;
}

.logo {
  width: 180px;
}

.text-logo {
  color: var(--primary-color-dark);
  margin-top: 10px;
  padding-top: 15px;
  padding-bottom: 15px;
  margin-bottom: 15px;
  padding-left: 15px;
  padding-right: 15px;
  text-decoration: none;
}

.title-text {
  font-size: 36px;
}

.section-search-bar {
  padding: 1em;
  margin-top: 16px;
}

.searchContainer {
  display: grid;
  grid-template-columns: 5fr 1fr;
  width: 300px;
  border: 1px solid var(--primary-color-dark);
  border-radius: 90px;

  overflow: hidden;
}

.searchContainer:focus-within i {
  display: none;
}

.searchContainer:focus-within .searchBox {
  grid-column: 1;
}

.searchContainer:focus-within .searchBox::placeholder {
  color: transparent;
}

.searchBox {
  border: 0;
  padding: 0.5rem;
  grid-column: 1/4;
  grid-row: 1;
  outline: none;
}

.searchButton {
  background: white;
  border: 0;

  color: Var(--primary-color-dark);
  padding: 0.5rem;
  grid-column: 4/5;
  grid-row: 1;
}

.searchButton:hover {
  color: var(--primary-color);
}

.header-dropdown-and-cart {
  padding: 1em;
  display: flex;
  margin-top: 16px;
  padding-right: 2em;
}

.text-logo {
  font-family: var(--title-font-family);
}

.button-cart-number {
  text-decoration: none;
  margin-top: 0.25em;
  border-radius: 90px 90px 90px 90px;
  display: flex;
  height: 34px;
  font-size: large;
}

.button.button.fas.fa-shopping-cart {
  padding-right: 0;
  font-size: larger;
}

.button.button.login {
  background-color: var(--primary-color-dark);
  margin-right: 1em;
  color: white;
  border-radius: 90px 90px 90px 90px;
  display: flex;
  margin-left: 0.5em;
  height: 40px;
}

.button.button.login:hover,
.button.button.login:active {
  background-color: var(--primary-color);
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}
</style>
