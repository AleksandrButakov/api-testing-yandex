package dto;

public class DtoOrders {
    private DtoOrder order;

    public DtoOrders(DtoOrder order) {
        this.order = order;
    }
    public DtoOrders() {
    }

    public DtoOrder getOrder() {
        return order;
    }

    public void setOrder(DtoOrder order) {
        this.order = order;
    }
}