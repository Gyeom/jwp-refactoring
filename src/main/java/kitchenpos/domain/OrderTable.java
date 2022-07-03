package kitchenpos.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tableGroupId;

    @Embedded
    private NumberOfGuest numberOfGuests;

    private boolean empty;

    protected OrderTable() {

    }


    public OrderTable(int numberOfGuests, boolean empty) {
        this.numberOfGuests = NumberOfGuest.of(numberOfGuests);
        this.empty = empty;
    }


    public OrderTable(Long tableGroupId, int numberOfGuests, boolean empty) {
        this(numberOfGuests, empty);
        this.tableGroupId = tableGroupId;
    }

    public OrderTable(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        this(tableGroupId, numberOfGuests, empty);
        this.id = id;
    }

    public static OrderTable of(int numberOfGuests, boolean empty) {
        return new OrderTable(numberOfGuests, empty);
    }

    public static OrderTable createOrderTable() {
        return new OrderTable();
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = NumberOfGuest.of(numberOfGuests);
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = NumberOfGuest.of(numberOfGuests);
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setTableGroupId(Long tableGroupId) {
        this.tableGroupId = tableGroupId;
    }

    public void changeEmptyTable() {
        this.empty = true;
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests.value();
    }

    public boolean isEmpty() {
        return empty;
    }
}
