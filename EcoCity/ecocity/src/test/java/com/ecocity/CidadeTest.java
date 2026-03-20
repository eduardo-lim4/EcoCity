package com.ecocity;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CidadeTest {

    @Test
    void testarLimites() {
        Cidade cidade = new Cidade();

        assertEquals(100, cidade.limitar(150));
        assertEquals(0, cidade.limitar(-10));
        assertEquals(50, cidade.limitar(50));   
    }
}
