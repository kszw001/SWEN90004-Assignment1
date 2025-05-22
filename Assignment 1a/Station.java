public class Station {
    private int id;
    private Cart cart;

    public Station(int i) {
        this.id = i;
        this.cart = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
