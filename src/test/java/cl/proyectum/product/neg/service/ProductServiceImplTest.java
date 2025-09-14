package cl.proyectum.product.neg.service;

import cl.proyectum.product.neg.entities.Product;
import cl.proyectum.product.neg.exceptions.NotFoundException;
import cl.proyectum.product.neg.repository.ProductRepository;
import cl.proyectum.product.neg.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductServiceImpl service;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private Product sample;

    @BeforeEach
    void setUp() {
        sample = new Product();
        sample.setId(1L);
        sample.setNameProduct("Teclado Mecánico");
        sample.setPriceProduct(new BigDecimal("49990"));
        sample.setStockProduct(25);
        sample.setDescriptionProduct("Switches rojos");
    }

    @Nested
    @DisplayName("create()")
    class CreateTests {
        @Test
        @DisplayName("debe guardar con id en null y retornar el persistido")
        void create_ok() {
            Product toCreate = new Product();
            toCreate.setId(999L);
            toCreate.setNameProduct("Mouse Gamer");
            toCreate.setPriceProduct(new BigDecimal("19990"));
            toCreate.setStockProduct(10);
            toCreate.setDescriptionProduct("RGB");
            Product saved = new Product();
            saved.setId(7L);
            saved.setNameProduct(toCreate.getNameProduct());
            saved.setPriceProduct(toCreate.getPriceProduct());
            saved.setStockProduct(toCreate.getStockProduct());
            saved.setDescriptionProduct(toCreate.getDescriptionProduct());
            given(repo.save(any(Product.class))).willReturn(saved);
            Product result = service.create(toCreate);
            then(repo).should().save(productCaptor.capture());
            Product passed = productCaptor.getValue();
            assertThat(passed.getId()).as("id debe ir en null al guardar").isNull();
            assertThat(result.getId()).isEqualTo(7L);
            assertThat(result.getNameProduct()).isEqualTo("Mouse Gamer");
        }
    }

    @Nested
    @DisplayName("get()")
    class GetTests {
        @Test
        @DisplayName("debe retornar el producto cuando existe")
        void get_found() {
            given(repo.findById(1L)).willReturn(Optional.of(sample));

            Product result = service.get(1L);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            then(repo).should().findById(1L);
        }

        @Test
        @DisplayName("debe lanzar NotFoundException cuando no existe")
        void get_notFound() {
            given(repo.findById(9L)).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.get(9L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Producto 9 no encontrado");

            then(repo).should().findById(9L);
        }
    }

    @Nested
    @DisplayName("list()")
    class ListTests {
        @Test
        @DisplayName("sin q (null, blank) debe usar findAll(pageable)")
        void list_noFilter() {
            Pageable pageable = PageRequest.of(0, 10, Sort.by("nameProduct"));
            Page<Product> page = new PageImpl<>(List.of(sample), pageable, 1);
            given(repo.findAll(pageable)).willReturn(page);
            Page<Product> resultNull = service.list(null, pageable);
            assertThat(resultNull.getTotalElements()).isEqualTo(1);
            then(repo).should().findAll(pageable);
            reset(repo);
            given(repo.findAll(pageable)).willReturn(page);
            Page<Product> resultBlank = service.list("   ", pageable);
            assertThat(resultBlank.getTotalElements()).isEqualTo(1);
            then(repo).should().findAll(pageable);
        }

        @Test
        @DisplayName("con q debe usar findByNameContainingIgnoreCase(q.trim(), pageable)")
        void list_withFilter() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<Product> page = new PageImpl<>(List.of(sample), pageable, 1);
            given(repo.findByNameContainingIgnoreCase("meca", pageable)).willReturn(page);
            Page<Product> result = service.list("  meca  ", pageable);
            assertThat(result.getContent()).hasSize(1);
            then(repo).should().findByNameContainingIgnoreCase("meca", pageable);
        }
    }

    @Nested
    @DisplayName("update()")
    class UpdateTests {
        @Test
        @DisplayName("debe actualizar campos y guardar")
        void update_ok() {
            Product incoming = new Product();
            incoming.setNameProduct("Teclado 60%");
            incoming.setPriceProduct(new BigDecimal("45990"));
            incoming.setStockProduct(30);
            incoming.setDescriptionProduct("Compacto");
            given(repo.findById(1L)).willReturn(Optional.of(sample));
            willAnswer(invocation -> invocation.getArgument(0))
                    .given(repo).save(any(Product.class));
            Product result = service.update(1L, incoming);
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getNameProduct()).isEqualTo("Teclado 60%");
            assertThat(result.getPriceProduct()).isEqualByComparingTo("45990");
            assertThat(result.getStockProduct()).isEqualTo(30);
            assertThat(result.getDescriptionProduct()).isEqualTo("Compacto");
            then(repo).should().findById(1L);
            then(repo).should().save(sample);
        }

        @Test
        @DisplayName("debe lanzar NotFoundException si el id no existe")
        void update_notFound() {
            given(repo.findById(99L)).willReturn(Optional.empty());
            Product incoming = new Product();
            incoming.setNameProduct("X");
            incoming.setPriceProduct(BigDecimal.ONE);
            incoming.setStockProduct(1);
            incoming.setDescriptionProduct("Y");
            assertThatThrownBy(() -> service.update(99L, incoming))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Producto 99 no encontrado");
            then(repo).should().findById(99L);
            then(repo).should(never()).save(any());
        }
    }

    @Nested
    @DisplayName("delete()")
    class DeleteTests {
        @Test
        @DisplayName("debe eliminar cuando existe")
        void delete_ok() {
            given(repo.existsById(1L)).willReturn(true);
            willDoNothing().given(repo).deleteById(1L);
            service.delete(1L);
            then(repo).should().existsById(1L);
            then(repo).should().deleteById(1L);
        }

        @Test
        @DisplayName("debe lanzar NotFoundException cuando no existe")
        void delete_notFound() {
            given(repo.existsById(123L)).willReturn(false);
            assertThatThrownBy(() -> service.delete(123L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Producto 123 no encontrado");
            then(repo).should().existsById(123L);
            then(repo).should(never()).deleteById(anyLong());
        }
    }
}

