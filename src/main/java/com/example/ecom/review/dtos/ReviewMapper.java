package com.example.ecom.review.dtos;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.ecom.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
  ReviewDto toDto(Review review);

  @Mapping(target = "id", ignore = true)
  Review toEntity(CreateReviewDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "reviewerName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(UpdateReviewDto updateDto, @MappingTarget Review review);

}
