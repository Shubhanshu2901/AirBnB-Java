package com.Java24GroupProject.AirBnBPlatform.controllers;

import com.Java24GroupProject.AirBnBPlatform.DTOs.BookingRequest;
import com.Java24GroupProject.AirBnBPlatform.DTOs.BookingResponse;
import com.Java24GroupProject.AirBnBPlatform.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.getBookingById(id);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    //get all bookings for a listing
    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByListingId(@PathVariable String listingId) {
        List<BookingResponse> bookingResponses = bookingService.getBookingsByListingId(listingId);
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }

    //get all bookings for current user
    @GetMapping("/user")
    public ResponseEntity<List<BookingResponse>> getBookingsCurrentUser() {
        List<BookingResponse> bookingResponses = bookingService.getBookingsCurrentUser();
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable String id, @Valid @RequestBody BookingRequest updatedBookingRequest) {
        BookingResponse bookingResponse = bookingService.updateBooking(id, updatedBookingRequest);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    @PatchMapping("/accept/{id}")
    public ResponseEntity<BookingResponse> acceptBooking(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.acceptOrRejectBooking(id, true);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<BookingResponse> rejectBooking(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.acceptOrRejectBooking(id, false);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    //ADMIN-specific endpoints ----------------------------------------------------------------------------

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookingResponses = bookingService.getAllBookings();
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }

    //get all user bookings by userid
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable String userId) {
        List<BookingResponse> bookingResponses = bookingService.getBookingsByUserId(userId);
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }
}