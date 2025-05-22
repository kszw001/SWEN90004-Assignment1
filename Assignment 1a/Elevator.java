import java.util.Objects;

public class Elevator {
    public enum Status {
        TOP,
        BOTTOM
    }


    private Cart cart;
    private Status status;

    public Elevator() {
        this.cart = null;
        this.status = Status.TOP;
    }

    public Cart depart() {
        up();
        return popCart();
    }

    public void arrive(Cart cart) {
        up();
        this.cart = cart;
    }

    public void operate() {
        if (Status.TOP.equals(status))
            down();
        else
            up();
    }

    private void up() {
        if (Status.TOP.equals(status))
            return;

        status = Status.TOP;
        if (Objects.isNull(cart)) {
            System.out.println("elevator ascends (empty)");
        } else {
            System.out.println("elevator ascends with " + cart);
        }

    }

    private void down() {
        if (Status.BOTTOM.equals(status))
            return;

        status = Status.BOTTOM;
        if (Objects.isNull(cart)) {
            System.out.println("elevator descends (empty)");
        } else {
            System.out.println("elevator descends with " + cart);
        }
    }


    public void pushCartFromStation(Cart cart) {
        down();
        this.cart = cart;
    }

    public Cart popCartToStation() {
        down();
        return popCart();

    }

    private Cart popCart() {
        Cart thisCart = cart;
        this.cart = null;
        return thisCart;
    }

    public boolean isEmpty() {
        return Objects.isNull(cart);
    }

    public Cart getCart() {
        return cart;
    }
}
