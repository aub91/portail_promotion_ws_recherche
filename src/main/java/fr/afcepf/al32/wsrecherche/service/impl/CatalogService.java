package fr.afcepf.al32.wsrecherche.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.afcepf.al32.groupe2.ws.dto.CategoryProductDto;
import fr.afcepf.al32.groupe2.ws.dto.PromotionDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByCategoryAndKeywordsResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByCategoryResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByKeywordsResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByShopResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.ShopDto;
import fr.afcepf.al32.wsrecherche.dao.itf.IRechercheProduits;
import fr.afcepf.al32.wsrecherche.entity.BaseProduct;
import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;
import fr.afcepf.al32.wsrecherche.service.itf.ICatalogService;
import fr.afcepf.al32.wsrecherche.service.itf.IServiceCategoryProduct;
import fr.afcepf.al32.wsrecherche.service.itf.IServicePromotion;

@Component
@Transactional
public class CatalogService implements ICatalogService {
	@Autowired
	IServicePromotion promotionService;

	@Autowired
	IRechercheProduits rechercheProduitsDao;

	@Autowired
	IServiceCategoryProduct categoryService;

	@Override
	public List<Promotion> getAllDisplayablePromotion() {
		List<Promotion> list = promotionService.findAllValid();
		return list.stream()
				.filter(promotion -> filterOnDate(promotion))
				.collect(Collectors.toList());
	}

	@Override
	public List<CategoryProduct> getAllRootCategories() {
		return categoryService.getAllRootCategories();
	}

	@Override
	public List<SearchByCategoryAndKeywordsResponseDto> searchByCategoryAndKeyWords(CategoryProductDto categoryProductDto, List<String> keyWords) {

		List<BaseProduct> products = rechercheProduitsDao.rechercherProduitSurMotsCles(keyWords);
		List<Promotion> list = promotionService.getAllValidPromotionByProduct(products);
		Stream<Promotion> stream = categoryProductDto == null? list.stream() : list.stream().filter(promotion -> filterOnCategoryName(categoryProductDto, promotion));
		List<PromotionDto> listePromotionDto= stream.filter(promotion -> filterOnDate(promotion))
				.map(promotion-> new PromotionDto(promotion.getId()))
				.collect(Collectors.toList());

		SearchByCategoryAndKeywordsResponseDto searchByCategoryAndKeywordsResponseDto = new SearchByCategoryAndKeywordsResponseDto();
		searchByCategoryAndKeywordsResponseDto.setCategoryProductDto(categoryProductDto);
		searchByCategoryAndKeywordsResponseDto.setPromotionsDto(listePromotionDto);
		searchByCategoryAndKeywordsResponseDto.setKeyWords(keyWords);

		List<SearchByCategoryAndKeywordsResponseDto> searchByCategoryAndKeywordsResponseDtos = new ArrayList<SearchByCategoryAndKeywordsResponseDto>();
		searchByCategoryAndKeywordsResponseDtos.add(searchByCategoryAndKeywordsResponseDto);
		return searchByCategoryAndKeywordsResponseDtos;


	}

	@Override
	public List<SearchByKeywordsResponseDto> searchByKeyWords(List<String> keyWords) {

		List<BaseProduct> products = rechercheProduitsDao.rechercherProduitSurMotsCles(keyWords);
		List<Promotion> list = promotionService.getAllValidPromotionByProduct(products);
		SearchByKeywordsResponseDto searchByKeywordsResponseDto = new SearchByKeywordsResponseDto();
		List<PromotionDto> listePromotionDto=  list.stream()
				.filter(promotion -> filterOnDate(promotion))
				.map(promotion-> new PromotionDto(promotion.getId()))
				.collect(Collectors.toList());

		searchByKeywordsResponseDto.setPromotionsDto(listePromotionDto);
		searchByKeywordsResponseDto.setKeyWords(keyWords);

		List<SearchByKeywordsResponseDto> searchByKeywordsResponseDtos = new ArrayList<SearchByKeywordsResponseDto>();
		searchByKeywordsResponseDtos.add(searchByKeywordsResponseDto);

		return searchByKeywordsResponseDtos ;
	}




	@Override
	public List<SearchByCategoryResponseDto> searchByCategory(CategoryProductDto categoryProductDto) {
		List<Promotion> list = getAllDisplayablePromotion();
		SearchByCategoryResponseDto searchByCategoryResponseDto = new SearchByCategoryResponseDto();
		List<PromotionDto> listePromotionDto=  list.stream()
				.filter(promotion -> filterOnCategoryName(categoryProductDto, promotion))
				.map(promotion-> new PromotionDto(promotion.getId()))
				.collect(Collectors.toList());

		searchByCategoryResponseDto.setPromotionsDto(listePromotionDto);
		searchByCategoryResponseDto.setCategoryProductDto(categoryProductDto);

		List<SearchByCategoryResponseDto> searchByCategoryResponseDtos = new ArrayList<SearchByCategoryResponseDto>();
		searchByCategoryResponseDtos.add(searchByCategoryResponseDto);
		return searchByCategoryResponseDtos;



	}

	private boolean filterOnCategoryName(CategoryProductDto categoryProductDto, Promotion promotion){
		Long categoryId = categoryProductDto.getId();
		CategoryProduct promotionCategory = promotion.getBaseProduct().getReferenceProduct().getCategoriesProduct();
		return isSubCategoryOf(categoryId, promotionCategory);
	}

	private boolean isSubCategoryOf(Long targetCategoryId, CategoryProduct categoryToCompare){
		if(categoryToCompare.getId().equals(targetCategoryId)){
			return true;
		} else if (categoryToCompare.getCategoryMum() != null){
			return isSubCategoryOf(targetCategoryId, categoryToCompare.getCategoryMum());
		} else {
			return false;
		}
	}


	@Override
	public List<SearchByShopResponseDto> searchByShop(List<ShopDto> shops) { 
		List<Promotion> list = promotionService.getAllValidPromotionByShop(shops);
		SearchByShopResponseDto searchByShopResponseDto = new SearchByShopResponseDto();
		List<PromotionDto> listePromotionDto= list.stream()
				.filter(promotion -> filterOnDate(promotion))
				.map(promotion-> new PromotionDto(promotion.getId()))
				.collect(Collectors.toList());

		searchByShopResponseDto.setPromotionsDto(listePromotionDto);
		searchByShopResponseDto.setShopDtos(shops);
		List<SearchByShopResponseDto> shopResponseDtos = new ArrayList<SearchByShopResponseDto>();
		
		return shopResponseDtos; 
	}

	private boolean filterOnDate(Promotion promotion) {
		LocalDateTime publishDate = promotion.getPublish().getPublishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return LocalDateTime.now().isBefore(publishDate.plus(promotion.getLimitTimePromotion()));
	}
}
