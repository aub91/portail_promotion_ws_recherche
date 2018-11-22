package fr.afcepf.al32.wsrecherche.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.afcepf.al32.wsrecherche.dao.itf.IRechercheProduits;
import fr.afcepf.al32.wsrecherche.dto.CategoryProductDto;
import fr.afcepf.al32.wsrecherche.dto.PromotionDto;
import fr.afcepf.al32.wsrecherche.dto.ShopDto;
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
	public List<PromotionDto> searchByCategoryAndKeyWords(CategoryProductDto categoryProductDto, List<String> keyWords) {

		List<BaseProduct> products = rechercheProduitsDao.rechercherProduitSurMotsCles(keyWords);
		List<Promotion> list = promotionService.getAllValidPromotionByProduct(products);

		Stream<Promotion> stream = categoryProductDto == null? list.stream() : list.stream().filter(promotion -> filterOnCategoryName(categoryProductDto, promotion));
		return stream.filter(promotion -> filterOnDate(promotion)).map(promotion-> new PromotionDto(promotion))
				.collect(Collectors.toList());
	}


	@Override
	public List<PromotionDto> searchByKeyWords(List<String> keyWords) {

		List<BaseProduct> products = rechercheProduitsDao.rechercherProduitSurMotsCles(keyWords);
		List<Promotion> list = promotionService.getAllValidPromotionByProduct(products);

		return list.stream()
				.filter(promotion -> filterOnDate(promotion)).map(promotion-> new PromotionDto(promotion))
				.collect(Collectors.toList());
	}
	@Override
	public List<PromotionDto> searchByCategory(CategoryProductDto categoryProductDto) {
		List<Promotion> list = getAllDisplayablePromotion();
		return list.stream().filter(promotion -> filterOnCategoryName(categoryProductDto, promotion)).map(promotion-> new PromotionDto(promotion)).collect(Collectors.toList());
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
	public List<PromotionDto> searchByShop(List<ShopDto> shops) { 
		List<Promotion> list = promotionService.getAllValidPromotionByShop(shops);
		return list.stream().filter(promotion -> filterOnDate(promotion)).map(promotion-> new PromotionDto(promotion)).collect(Collectors.toList());
	}
	
	private boolean filterOnDate(Promotion promotion) {
		LocalDateTime publishDate = promotion.getPublish().getPublishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return LocalDateTime.now().isBefore(publishDate.plus(promotion.getLimitTimePromotion()));
	}
}
