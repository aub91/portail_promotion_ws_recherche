package fr.afcepf.al32.wsrecherche.service.itf;

import java.util.List;

import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;
import fr.afcepf.al32.wsrecherche.entity.Shop;

public interface ICatalogService {
	
	List<Promotion> getAllDisplayablePromotion();
	
	List<CategoryProduct> getAllRootCategories();

	List<Promotion> searchByCategoryAndKeyWords(CategoryProduct selectedCategory, List<String> keyWords);

	List<Promotion> searchByCategory(CategoryProduct category);

	List<Promotion> searchByKeyWords(List<String> keyWords);

	List<Promotion> searchByShop(List<Shop> shop);

}
