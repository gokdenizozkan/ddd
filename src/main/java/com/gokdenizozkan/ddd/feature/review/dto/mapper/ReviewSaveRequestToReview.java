package com.gokdenizozkan.ddd.feature.review.dto.mapper;

import com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.feature.review.Review;
import com.gokdenizozkan.ddd.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.feature.store.Store;
import com.gokdenizozkan.ddd.feature.store.StoreRepository;
import com.gokdenizozkan.ddd.feature.user.buyer.Buyer;
import com.gokdenizozkan.ddd.feature.user.buyer.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ReviewSaveRequestToReview implements Function<ReviewSaveRequest, Review> {
    private final BuyerRepository buyerRepository;
    private final StoreRepository storeRepository;

    @Override
    public Review apply(ReviewSaveRequest reviewSaveRequest) {
        Review review = new Review();

        review.setRating(reviewSaveRequest.rating());
        review.setExperience(reviewSaveRequest.experience());
        review.setBuyer(buyerRepository.findById(reviewSaveRequest.buyerId())
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Buyer.class, reviewSaveRequest.buyerId())));
        review.setStore(storeRepository.findById(reviewSaveRequest.storeId())
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, reviewSaveRequest.storeId())));

        return review;
    }
}
