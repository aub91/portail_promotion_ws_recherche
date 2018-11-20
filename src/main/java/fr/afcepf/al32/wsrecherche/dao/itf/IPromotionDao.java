package fr.afcepf.al32.wsrecherche.dao.itf;

import java.util.List;

import fr.afcepf.al32.wsrecherche.entity.Promotion;

public interface IPromotionDao extends IGenericDao<Promotion> {
	List<Promotion> findAllValid();

	void createOne(Promotion promotion);
}
