package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**

* Hibernate implementation of DonationCamp Model
* @author malay
  */

public class DonationCampModelHibImpl implements DonationCampModelInt {

// -------------------- ADD --------------------
public long add(DonationCampDTO dto) throws ApplicationException, DuplicateRecordException {


DonationCampDTO existDto = findByCampName(dto.getCampName());
if (existDto != null) {
    throw new DuplicateRecordException("Camp Name already exists");
}

Session session = HibDataSource.getSession();
Transaction tx = null;

try {
    tx = session.beginTransaction();
    session.save(dto);
    tx.commit();
} catch (HibernateException e) {
    if (tx != null)
        tx.rollback();
    throw new ApplicationException("Exception in Donation Camp Add " + e.getMessage());
} finally {
    session.close();
}

return dto.getId();


}

// -------------------- DELETE --------------------
public void delete(DonationCampDTO dto) throws ApplicationException {


Session session = null;
Transaction tx = null;

try {
    session = HibDataSource.getSession();
    tx = session.beginTransaction();
    session.delete(dto);
    tx.commit();
} catch (HibernateException e) {
    if (tx != null)
        tx.rollback();
    throw new ApplicationException("Exception in Donation Camp Delete " + e.getMessage());
} finally {
    session.close();
}


}

// -------------------- UPDATE --------------------
public void update(DonationCampDTO dto) throws ApplicationException, DuplicateRecordException {


DonationCampDTO existDto = findByCampName(dto.getCampName());

if (existDto != null && existDto.getId() != dto.getId()) {
    throw new DuplicateRecordException("Camp Name already exists");
}

Session session = null;
Transaction tx = null;

try {
    session = HibDataSource.getSession();
    tx = session.beginTransaction();
    session.saveOrUpdate(dto);
    tx.commit();
} catch (HibernateException e) {
    if (tx != null)
        tx.rollback();
    throw new ApplicationException("Exception in Donation Camp Update " + e.getMessage());
} finally {
    session.close();
}


}

// -------------------- FIND BY PK --------------------
public DonationCampDTO findByPK(long pk) throws ApplicationException {


Session session = null;
DonationCampDTO dto = null;

try {
    session = HibDataSource.getSession();
    dto = (DonationCampDTO) session.get(DonationCampDTO.class, pk);
} catch (HibernateException e) {
    throw new ApplicationException("Exception in getting Donation Camp by PK");
} finally {
    session.close();
}

return dto;


}

// -------------------- FIND BY CAMP NAME --------------------
public DonationCampDTO findByCampName(String campName) throws ApplicationException {


Session session = null;
DonationCampDTO dto = null;

try {
    session = HibDataSource.getSession();
    Criteria criteria = session.createCriteria(DonationCampDTO.class);
    criteria.add(Restrictions.eq("campName", campName));

    List list = criteria.list();
    if (list.size() == 1) {
        dto = (DonationCampDTO) list.get(0);
    }

} catch (HibernateException e) {
    throw new ApplicationException("Exception in getting Camp by Name " + e.getMessage());
} finally {
    session.close();
}

return dto;


}

// -------------------- LIST --------------------
public List list() throws ApplicationException {
return list(0, 0);
}

public List list(int pageNo, int pageSize) throws ApplicationException {


Session session = null;
List list = null;

try {
    session = HibDataSource.getSession();
    Criteria criteria = session.createCriteria(DonationCampDTO.class);

    if (pageSize > 0) {
        pageNo = (pageNo - 1) * pageSize;
        criteria.setFirstResult(pageNo);
        criteria.setMaxResults(pageSize);
    }

    list = criteria.list();

} catch (HibernateException e) {
    throw new ApplicationException("Exception in Donation Camp List");
} finally {
    session.close();
}

return list;


}

// -------------------- SEARCH --------------------
public List search(DonationCampDTO dto) throws ApplicationException {
return search(dto, 0, 0);
}

public List search(DonationCampDTO dto, int pageNo, int pageSize) throws ApplicationException {


Session session = null;
List list = null;

try {
    session = HibDataSource.getSession();
    Criteria criteria = session.createCriteria(DonationCampDTO.class);

    if (dto != null) {

        if (dto.getId() != null) {
            criteria.add(Restrictions.eq("id", dto.getId()));
        }

        if (dto.getCampName() != null && dto.getCampName().length() > 0) {
            criteria.add(Restrictions.like("campName", dto.getCampName() + "%"));
        }

        if (dto.getCampDate() != null) {
            criteria.add(Restrictions.eq("campDate", dto.getCampDate()));
        }

        if (dto.getOrganizer() != null && dto.getOrganizer().length() > 0) {
            criteria.add(Restrictions.like("organizer", dto.getOrganizer() + "%"));
        }
    }

    if (pageSize > 0) {
        pageNo = (pageNo - 1) * pageSize;
        criteria.setFirstResult(pageNo);
        criteria.setMaxResults(pageSize);
    }

    list = criteria.list();

} catch (HibernateException e) {
    throw new ApplicationException("Exception in Donation Camp Search");
} finally {
    session.close();
}

return list;


}

}
