package fr.afcepf.al32.wsrecherche.service.itf;

import java.util.List;

import fr.afcepf.al32.groupe2.ws.dto.CategoryProductDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByCategoryAndKeywordsResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByCategoryResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByKeywordsResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.SearchByShopResponseDto;
import fr.afcepf.al32.groupe2.ws.dto.ShopDto;
import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;

public interface ICatalogService {

	List<Promotion> getAllDisplayablePromotion();

	List<CategoryProduct> getAllRootCategories();

	List<SearchByCategoryAndKeywordsResponseDto> searchByCategoryAndKeyWords( CategoryProductDto categoryProductDto, List<String> keyWords);

	List<SearchByCategoryResponseDto> searchByCategory(CategoryProductDto categoryProductDto);

	List<SearchByKeywordsResponseDto> searchByKeyWords(List<String> keyWords);

	List<SearchByShopResponseDto> searchByShop(List<ShopDto> shops);



}
