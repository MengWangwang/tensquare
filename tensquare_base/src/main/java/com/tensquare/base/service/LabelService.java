package com.tensquare.base.service;

import com.common.util.IdWorker;
import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label){
       return labelDao.findAll(new Specification<Label>() {
           /*
           *@param root 根对象，也就是要把条件封装到哪个对象中。 where 类名=label.getid
           *@param query 封装的都是查询关键字，比如group by order by 等
           *@param cb 用来封装条件对象的，如果直接返回null,表示不需要任何条件
            *
           * */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();

                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate= criteriaBuilder.like(root.get("labelname").as(String.class), "%"+label.getLabelname()+"%");//where labelname like "%"+label.getLabelname()+"%"
                    list.add(predicate);
                }

                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate= criteriaBuilder.like(root.get("state").as(String.class), label.getState());//where labelname like "%"+label.getLabelname()+"%"
                    list.add(predicate);
                }

                Predicate[] parr =new Predicate[list.size()];
                list.toArray(parr);

                return criteriaBuilder.and(parr);
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);

        return labelDao.findAll(new Specification<Label>() {
            /*
             *@param root 根对象，也就是要把条件封装到哪个对象中。 where 类名=label.getid
             *@param query 封装的都是查询关键字，比如group by order by 等
             *@param cb 用来封装条件对象的，如果直接返回null,表示不需要任何条件
             *
             * */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();

                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate= criteriaBuilder.like(root.get("labelname").as(String.class), "%"+label.getLabelname()+"%");//where labelname like "%"+label.getLabelname()+"%"
                    list.add(predicate);
                }

                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate= criteriaBuilder.like(root.get("state").as(String.class), label.getState());//where labelname like "%"+label.getLabelname()+"%"
                    list.add(predicate);
                }

                Predicate[] parr =new Predicate[list.size()];
                list.toArray(parr);

                return criteriaBuilder.and(parr);
            }
        }, pageable);
    }
}
