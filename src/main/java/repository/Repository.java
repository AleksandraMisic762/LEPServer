package repository;

import java.util.List;

public interface Repository<T> {
    
    void uspostaviKonekcijuSaBazom() throws Exception;
    
    void raskiniKonekcijuSaBazom() throws Exception;
    
    void potvrdiTransakciju() throws Exception;
    
    void ponistiTransakciju() throws Exception;
        
    T add(T param) throws Exception;
    
    T edit(T param) throws Exception;
    
    void delete(T param)throws Exception;
    
    T get(T param) throws Exception;
    
    List<T> getAll(T param) throws Exception;
    
    List<T> getWhere(T param, String where) throws Exception;
}
