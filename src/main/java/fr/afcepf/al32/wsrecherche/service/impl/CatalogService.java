package fr.afcepf.al32.wsrecherche.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
				.filter(promotion -> {
			LocalDateTime publishDate = promotion.getPublish().getPublishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return LocalDateTime.now().isBefore(publishDate.plus(promotion.getLimitTimePromotion()));
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<CategoryProduct> getAllRootCategories() {
		return categoryService.getAllRootCategories();
	}

	@Override
	public List<Promotion> searchByCategoryAndKeyWords(CategoryProduct selectedCategory, List<String> keyWords) {
		
		List<BaseProduct> products = rechercheProduitsDao.rechercherProduitSurMotsCles(keyWords);
		List<Promotion> list = promotionService.getAllValidPromotionByProduct(products);
		
		return list.stream()
				.filter(promotion -> {
			LocalDateTime publishDate = promotion.getPublish().getPublishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return LocalDateTime.now().isBefore(publishDate.plus(promotion.getLimitTimePromotion()));
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<Promotion> searchByCategory(CategoryProduct category) {
		List<Promotion> list = getAllDisplayablePromotion();
		return list.stream().filter(promotion -> category.equals(promotion.getBaseProduct().getReferenceProduct().getCategoriesProduct())).collect(Collectors.toList());
	}
	
	
}
