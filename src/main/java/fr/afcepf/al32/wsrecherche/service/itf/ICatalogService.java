package fr.afcepf.al32.wsrecherche.service.itf;

import java.util.List;

import fr.afcepf.al32.wsrecherche.dto.CategoryProductDto;
import fr.afcepf.al32.wsrecherche.dto.PromotionDto;
import fr.afcepf.al32.wsrecherche.dto.ShopDto;
import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;

public interface ICatalogService {
	
	List<Promotion> getAllDisplayablePromotion();
	
	List<CategoryProduct> getAllRootCategories();

	List<PromotionDto> searchByKeyWords(List<String> keyWords);

	List<PromotionDto> searchByCategory(CategoryProductDto categoryProductDto);

	List<PromotionDto> searchByCategoryAndKeyWords(CategoryProductDto categoryProductDto, List<String> keyWords);

	List<PromotionDto> searchByShop(List<ShopDto> shops);

}
