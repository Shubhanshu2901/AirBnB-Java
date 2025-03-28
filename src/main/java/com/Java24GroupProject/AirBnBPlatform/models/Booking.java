package com.Java24GroupProject.AirBnBPlatform.models;


import com.Java24GroupProject.AirBnBPlatform.models.supportClasses.BookingStatus;
import com.Java24GroupProject.AirBnBPlatform.models.supportClasses.DateRange;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    @DBRef
    @NotNull(message = "listing is required")
    private Listing listing;

    private String listingTitle;

    @DBRef
    @NotNull(message = "A user is required")
    private User user;

    @NotNull(message = "booking dates are required")
    private DateRange bookingDates;

    @NotNull(message = "number of guests is required")
    @Positive(message = "amount of guests must be greater than 0")
    private Integer numberOfGuests;

    // (number of days) * (price per night)
    @NotNull(message = "total price is required")
    @Positive(message = "total price must be greater than 0")
    private BigDecimal totalPrice;

    @NotNull(message = "booking status cannot be null")
    private BookingStatus bookingStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public Booking() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotNull(message = "A listing ID is required") Listing getListing() {
        return listing;
    }

    public void setListing(@NotNull(message = "A listing ID is required") Listing listing) {
        this.listing = listing;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public @NotNull(message = "A user is required") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "A user is required") User user) {
        this.user = user;
    }


    public @NotNull(message = "Number of guests is required") @Positive(message = "Amount of guests must be greater than 0") Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(@NotNull(message = "Number of guests is required") @Positive(message = "Amount of guests must be greater than 0") Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public @Positive(message = "Total price must be greater than 0") BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@Positive(message = "Total price must be greater than 0") BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DateRange getBookingDates() {
        return bookingDates;
    }

    public void setBookingDates(DateRange bookingDates) {
        this.bookingDates = bookingDates;
    }
}
