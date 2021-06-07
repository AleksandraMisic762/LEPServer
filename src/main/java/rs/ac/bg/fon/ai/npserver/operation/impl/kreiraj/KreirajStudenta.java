package rs.ac.bg.fon.ai.npserver.operation.impl.kreiraj;

import rs.ac.bg.fon.ai.npcommon.domain.Student;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class KreirajStudenta extends OpstaSO {
    
    Student student;

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof Student)) {
            throw new Exception("Preduslovi za kreiranje studenta nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        student = (Student) repository.add((Student)param);
    }

    public Student getStudent() {
        return student;
    }

}
