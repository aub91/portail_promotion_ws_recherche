package fr.afcepf.al32.wsrecherche.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afcepf.al32.wsrecherche.dto.CategoryProductDto;
import fr.afcepf.al32.wsrecherche.dto.PromotionDto;
import fr.afcepf.al32.wsrecherche.dto.SearchPromotionAndCategoryDto;
import fr.afcepf.al32.wsrecherche.dto.ShopDto;
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

	@GetMapping("/byCategoryAndKeywords")
	public List<PromotionDto> getSearchByCategoryAndKeywords(@RequestBody SearchPromotionAndCategoryDto searchPromotionAndCategoryDto) {

		return catalogService.searchByCategoryAndKeyWords(searchPromotionAndCategoryDto.getCategoryProductDto(),searchPromotionAndCategoryDto.getKeyWords());

	}

	@GetMapping("/byKeywords")
	public List<PromotionDto> getSearchByKeywords(@RequestBody List<String> keyWords) {

		return catalogService.searchByKeyWords(keyWords);
	}

	@GetMapping("/byCategory")
	public List<PromotionDto> getSearchByCategory(@RequestBody CategoryProductDto categoryProductDto) {

		return catalogService.searchByCategory(categoryProductDto);

	}


	@GetMapping("/byShop")
	public List<PromotionDto> getSearchByShop(@RequestBody List<ShopDto> shop) {
		return catalogService.searchByShop(shop);
	}

}
