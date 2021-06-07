package repository.db;

import repository.Repository;

public interface DBRepository<T> extends Repository<T>{
    
    @Override
    default public void uspostaviKonekcijuSaBazom() throws Exception{
        DBConnectionFactory.getInstance().getConnection();
    }
    
    @Override
    default public void raskiniKonekcijuSaBazom() throws Exception{
        DBConnectionFactory.getInstance().getConnection().close();
    }
    
    @Override
    default public void potvrdiTransakciju() throws Exception{
        DBConnectionFactory.getInstance().getConnection().commit();
    }
    
    @Override
    default public void ponistiTransakciju() throws Exception{
        DBConnectionFactory.getInstance().getConnection().rollback();
    }
}