package fr.afcepf.al32.wsrecherche.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afcepf.al32.wsrecherche.dto.SearchPromotionAndCategoryDto;
import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;
import fr.afcepf.al32.wsrecherche.entity.Shop;
import fr.afcepf.al32.wsrecherche.service.itf.ICatalogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@RestController
@RequestMapping(value="/rest/promotionProduct", headers="Accept=application/json")
public class CatalogRestCtrl {


	@Autowired
	private ICatalogService catalogService;
	
	@GetMapping("")
	public List<Promotion> getSearchByCategoryAndKeywords(@RequestBody SearchPromotionAndCategoryDto searchPromotionAndCategoryDto) {
		
		return catalogService.searchByCategoryAndKeyWords(searchPromotionAndCategoryDto.getSelectedCategory(),searchPromotionAndCategoryDto.getKeyWords());
	
	}
	
	@GetMapping("")
	public List<Promotion> getSearchByKeywords(@RequestBody List<String> keyWords) {
		
		return catalogService.searchByKeyWords(keyWords);
	
	}
	
	@GetMapping("")
	public List<Promotion> getSearchByCategory(@RequestBody CategoryProduct category) {
	 
			return catalogService.searchByCategory(category);
	 
	}
	
	
	@GetMapping("")
	public List<Promotion> getSearchByShop(@RequestBody List<Shop> shop) {
	 
			return catalogService.searchByShop(shop);
	 
	}
 
}
