public class Cart {
    private static int nowId = 0;

    public static Cart getNewCart() {

        return new Cart(++nowId);
    }

    private int gem;
    private int id;

    public Cart(int id) {
        this.id = id;
        this.gem = 0;
    }

    public int getGem() {
        return gem;
    }

    public void setGem(int gem) {
        this.gem = gem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("cart [%d: %d]", id, gem);
    }
}
