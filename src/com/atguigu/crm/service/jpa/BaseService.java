package com.atguigu.crm.service.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.BaseRepository;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.Product;

public class BaseService<T,PK extends Serializable>{
	
	@Autowired
	private BaseRepository<T, PK> baseRepository;
	
	
	public Page<T> getPage(int pageNo,int pageSize,Map<String,Object> map){
		//1.创建pageRequest对象
		PageRequest page = new PageRequest(pageNo, pageSize);
		
		//2.将map转化为propertityFilter
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(map);
		
		//3.将filters转化为specification对象
		Specification<T> specification = parseFiltersToSpecification(filters);
		
		//4.返回page对象
		return baseRepository.findAll(specification, page);
	}

	
	private Specification<T> parseFiltersToSpecification(
			final List<PropertyFilter> filters) {
		
		
		Specification<T> specification = new Specification<T>() {
			
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) { 
				//Predicate: JPA Criteria 查询中的查询条件
				//Root: 实际查询的实体类, 可以导航到要查询的属性上.
				//CriteriaBuilder: JPA Criteria 查询的静态工厂类. 可以从中获取到 JPA Criteria 查询的很多实例
				Predicate predicate =null;
				
				if(filters!=null && filters.size()>0){
					List<Predicate> predicates = new ArrayList<>();
					
					for (PropertyFilter propertyFilter : filters) {
						//可能是级联属性: 例如: Customer 的 manager.name 属性. 
						String propertyName = propertyFilter.getPropertyName();
						//得到 JPA 中的 Expression. 以备后用
						String[] propertyNames = propertyName.split("\\.");
						Path expression =root.get(propertyNames[0]);
						
						if(propertyNames.length>0){
							for(int i=1;i<propertyNames.length;i++){
								expression = expression.get(propertyNames[i]);
							}
						}
						
						
						Class propertyType = propertyFilter.getPropertyType();
						Object propertyVal = propertyFilter.getPropertyVal();
						
						propertyVal = ReflectionUtils.convertValue(propertyVal, propertyType);
						
						//匹配类型
						MatchType matchType = propertyFilter.getMatchType();
						
						switch (matchType) {
						case EQ:
							predicate = builder.equal(expression, propertyVal);
							break;
						case GE:
							predicate = builder.ge(expression, (Number)propertyVal);
							break;
						case GT:
							predicate = builder.gt(expression, (Number)propertyVal);
							break;
						case LE:
							predicate = builder.le(expression, (Number)propertyVal);
							break;
						case LT:
							predicate = builder.lt(expression, (Number)propertyVal);
							break;
						case LIKE:
							predicate = builder.like(expression, "%" + propertyVal + "%");
							break;
						case ISNULL:
							predicate = builder.isNull(expression);
						default:
							break;
						}
						predicates.add(predicate);
					}
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return predicate;
			}
		};
		return specification;
	}

	
	/**
	 * 通用增加方法
	 * @param pro
	 */
	public void save(Product pro) {
		baseRepository.save((T)pro);
	}
	
	/**
	 * 通用改方法
	 */
	public void update(Product pro){
		baseRepository.saveAndFlush((T) pro);
	}
	
	/**
	 * 通用删除
	 */
	public void delete(Product pro){
		baseRepository.delete((T)pro);
	}
	
	/**
	 * 通用查单个对象
	 */
	public Product get(PK id){
		
		return (Product) baseRepository.findOne(id);
	}
	
}