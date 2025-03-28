package com.Java24GroupProject.AirBnBPlatform.models;


import com.Java24GroupProject.AirBnBPlatform.models.supportClasses.DateRange;
import com.Java24GroupProject.AirBnBPlatform.models.supportClasses.ListingUtilities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Document(collection = "listings")
public class Listing {
    @Id
    private String id;

    @NotNull(message = "listing title is a required field")
    @NotEmpty(message = "listing title is a required field")
    @NotBlank(message = "listing title is a required field")
    private String title;

    private String description;

    @NotNull(message = "pricePerNight is a required field")
    @Positive(message = "pricePerNight must be greater than zero")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal pricePerNight;

    @NotNull(message = "capacity is a required field")
    @Positive(message = "capacity must be greater than zero")
    private Integer capacity;

    private Set<ListingUtilities> utilities;

    @NotNull(message = "listing must have a host")
    @DBRef
    private User host;
    private String hostName;

    private List<String> imageUrls;

    @NotNull(message = "location is a required field")
    @NotEmpty(message = "location is a required field")
    @NotBlank(message = "location is a required field")
    private String location;

    private List<DateRange> availableDates;

    private Double averageRating;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Listing() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotNull(message = "listing title is a required field") @NotEmpty(message = "listing title is a required field") @NotBlank(message = "listing title is a required field") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "listing title is a required field") @NotEmpty(message = "listing title is a required field") @NotBlank(message = "listing title is a required field") String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull(message = "pricePerNight is a required field") @Positive(message = "pricePerNight must be greater than zero") BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(@NotNull(message = "pricePerNight is a required field") @Positive(message = "pricePerNight must be greater than zero") BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public @NotNull(message = "capacity is a required field") @Positive(message = "capacity must be greater than zero") Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "capacity is a required field") @Positive(message = "capacity must be greater than zero") Integer capacity) {
        this.capacity = capacity;
    }

    public Set<ListingUtilities> getUtilities() {
        return utilities;
    }

    public void setUtilities(Set<ListingUtilities> utilities) {
        this.utilities = utilities;
    }

    public @NotNull(message = "listing must have a host") User getHost() {
        return host;
    }

    public void setHost(@NotNull(message = "listing must have a host") User host) {
        this.host = host;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public @NotNull(message = "location is a required field") @NotEmpty(message = "location is a required field") @NotBlank(message = "location is a required field") String getLocation() {
        return location;
    }

    public void setLocation(@NotNull(message = "location is a required field") @NotEmpty(message = "location is a required field") @NotBlank(message = "location is a required field") String location) {
        this.location = location;
    }

    public List<DateRange> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<DateRange> availableDates) {
        this.availableDates = availableDates;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
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

    public void addAvailableDateRange(DateRange dateRange) {

        DateRange startsWhereNewDateRangeEnds = null;
        DateRange endWhereNewDateRangeStarts = null;

        for (DateRange availableDateRange : availableDates) {
            //check if there is overlap with existing dates
            if (dateRange.hasOverlapWithAnotherDateRange(availableDateRange)) {
                throw new IllegalArgumentException("dates could not be added, as they overlap with existing available date ranges");

                //if dates are identical to dates in list throw error
            } else if (dateRange.isIdenticalToAnotherDateRange(availableDateRange)) {
                throw new IllegalArgumentException("dates could not be added, already in available dates for listing");

                //does dateRange start at the end of existing date range
            } else if (dateRange.getStartDate().isEqual(availableDateRange.getEndDate())) {
                endWhereNewDateRangeStarts = availableDateRange;

                //does dateRange end at the start of existing date range
            } else if (availableDateRange.getStartDate().isEqual(dateRange.getEndDate())) {
                startsWhereNewDateRangeEnds = availableDateRange;
            }
        }

        //if new dateRange is not adjacent to any exising date range
        if (startsWhereNewDateRangeEnds == null && endWhereNewDateRangeStarts == null) {
            availableDates.add(dateRange);

            //if new daterange ends at existing daterange start date, extend existing adjacent daterange
        } else if (startsWhereNewDateRangeEnds != null && endWhereNewDateRangeStarts == null) {
            startsWhereNewDateRangeEnds.setStartDate(dateRange.getStartDate());
            //if new daterange starts at existing daterange end date, extend existing adjacent daterange
        } else if (startsWhereNewDateRangeEnds == null && endWhereNewDateRangeStarts != null) {
            endWhereNewDateRangeStarts.setEndDate(dateRange.getEndDate());
        //if it is adjacent to two existing dateranges
        } else {
            endWhereNewDateRangeStarts.setEndDate(startsWhereNewDateRangeEnds.getEndDate());
            availableDates.remove(startsWhereNewDateRangeEnds);
        }
    }
}