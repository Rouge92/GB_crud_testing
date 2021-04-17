package Services;

import com.github.javafaker.Faker;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Converter;
import ru.geekbrains.Parts.CategoryType;
import ru.geekbrains.dto.ErrorBody;
import ru.geekbrains.dto.Product;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.utils.RetrofitUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

public class PutProductTests {
    Integer productId;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;
    Product productUpd;
    Product emptyProduct;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        RestAssured.filters(new AllureRestAssured());
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);

    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int)(Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

    }

    @SneakyThrows
    @Test
    void updateAllProductFuelsTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withCategoryTitle(CategoryType.ELECTRONICS.getTitle())
                .withPrice((int)(Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isNotEqualTo(product.getTitle());
        assertThat(response3.body().getPrice()).isNotEqualTo(product.getPrice());
        assertThat(response3.body().getCategoryTitle()).isNotEqualTo(product.getCategoryTitle());

    }
    @SneakyThrows
    @Test
    void updateTitleProductFuelsTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle(faker.food().ingredient())
                .withCategoryTitle(response.body().getCategoryTitle());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isNotEqualTo(product.getTitle());
        assertThat(response3.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response3.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
    }
    @SneakyThrows
    @Test
    void updateLongTitleProductFuelsTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                        .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle(faker.food().ingredient())
                .withCategoryTitle((faker.lorem().fixedString(100)));
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isNotEqualTo(product.getTitle());
        assertThat(response3.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response3.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
    }

    @SneakyThrows
    @Test
    void updatePriceProductFuelsTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withPrice(456)
                .withCategoryTitle(response.body().getCategoryTitle());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response3.body().getPrice()).isNotEqualTo(product.getPrice());
        assertThat(response3.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
    }

    @SneakyThrows
    @Test
    void updateCategoryProductFuelsTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle(response.body().getTitle())
                .withPrice(response.body().getPrice())
                .withCategoryTitle(CategoryType.ELECTRONICS.getTitle());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response3.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response3.body().getCategoryTitle()).isNotEqualTo(product.getCategoryTitle());
    }

    @SneakyThrows
    @Test
    void updateWithEmptyFuelsTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId);
        retrofit2.Response<Product> response2 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isFalse();
        assertThat(response2.code()).isEqualTo(500);
        if (response2 != null && !response2.isSuccessful() && response2.errorBody() != null) {
            ResponseBody body = response2.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).contains("Internal Server Error");

        }
    }
    @SneakyThrows
    @Test
    void updateByNonexistentIdProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                .execute();
        assertThat(response2.isSuccessful()).isTrue();
        assert response2.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(1234)
                .withCategoryTitle(CategoryType.ELECTRONICS.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isTrue();
        assertThat(response3.code()).isEqualTo(400);
        if (response3 != null && !response3.isSuccessful() && response3.errorBody() != null) {
            ResponseBody body = response3.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getStatus().equals(400));
        }
    }




    @SneakyThrows
    @Test
    void updateEmptyTitleProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                        .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withPrice(response.body().getPrice())
                .withCategoryTitle(CategoryType.ELECTRONICS.getTitle());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isEqualTo(null);
        assertThat(response3.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response3.body().getCategoryTitle()).isNotEqualTo(product.getCategoryTitle());

    }

    @SneakyThrows
    @Test
    void updateEmptyPriceProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        retrofit2.Response<Product> response2 =
                productService.getProduct(productId)
                        .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response2.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response2.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle(response.body().getTitle())
                .withCategoryTitle(CategoryType.ELECTRONICS.getTitle());
        retrofit2.Response<Product> response3 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response3.isSuccessful()).isTrue();
        assert response3.body() != null;
        assertThat(response3.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response3.body().getPrice()).isEqualTo(0);
        assertThat(response3.body().getCategoryTitle()).isNotEqualTo(product.getCategoryTitle());

    }
    @SneakyThrows
    @Test
    void createMaxINTPriceProductTest() {
        emptyProduct = new Product().withTitle(faker.food().ingredient())
                .withPrice(2147483647)
                .withCategoryTitle("Food");

        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).isEqualTo("Bad Request");
        }
    }

    //Этот тест должен успасть
    @SneakyThrows
    @Test
    void createNegativePriceProductTest() {
        emptyProduct = new Product().withTitle(faker.food().ingredient())
                .withPrice(-450)
                .withCategoryTitle("Food");

        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).isEqualTo("Bad Request");
        }
    }
    @SneakyThrows
    @Test
    void updateEmptyCategoryProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle(product.getTitle())
                .withPrice(product.getPrice());
        retrofit2.Response<Product> response2 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isFalse();
        assertThat(response2.code()).isEqualTo(500);
        if (response2 != null && !response2.isSuccessful() && response2.errorBody() != null) {
            ResponseBody body = response2.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).contains("Internal Server Error");

        }
    }

    @SneakyThrows
    @Test
    void updateOnlyTitleProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle(product.getTitle());
        retrofit2.Response<Product> response2 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isFalse();
        assertThat(response2.code()).isEqualTo(500);
        if (response2 != null && !response2.isSuccessful() && response2.errorBody() != null) {
            ResponseBody body = response2.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).contains("Internal Server Error");

        }
    }

    @SneakyThrows
    @Test
    void updateOnlyPriceProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withPrice(product.getPrice());
        retrofit2.Response<Product> response2 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isFalse();
        assertThat(response2.code()).isEqualTo(500);
        if (response2 != null && !response2.isSuccessful() && response2.errorBody() != null) {
            ResponseBody body = response2.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).contains("Internal Server Error");

        }
    }
    @SneakyThrows
    @Test
    void updateOnlyCategoryProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withCategoryTitle(CategoryType.ELECTRONICS.getTitle());
        retrofit2.Response<Product> response2 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isTrue();
        assert response2.body() != null;
        assertThat(response2.body().getTitle()).isEqualTo(null);
        assertThat(response2.body().getPrice()).isEqualTo(0);
        assertThat(response2.body().getCategoryTitle()).isEqualTo(productUpd.getCategoryTitle());
    }


    @SneakyThrows
    @Test
    void updateOnlyLongTitleProductTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product)
                        .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
        productService.getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        productUpd = new Product()
                .withId(productId)
                .withTitle((faker.lorem().fixedString(100)));
        retrofit2.Response<Product> response2 =
                productService.updateProduct(productUpd)
                        .execute();
        assertThat(response2.isSuccessful()).isFalse();
        assertThat(response2.code()).isEqualTo(500);
        if (response2 != null && !response2.isSuccessful() && response2.errorBody() != null) {
            ResponseBody body = response2.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).contains("Internal Server Error");

        }
    }


    @AfterEach
    void tearDown() {
        if (productId!=null)
        try {
            retrofit2.Response<ResponseBody> response =
                    productService.deleteProduct(productId)
                            .execute();
            assertThat(response.isSuccessful()).isTrue();
        } catch (IOException e) {

        }
    }
}
