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

public class PostProductTests {
    Integer productId;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;
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
    void createNewProductTest() {
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

    }


    @SneakyThrows
    @Test
    void createNewProductNegativeTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product.withId(555))
                        .execute();
        assertThat(response.code()).isEqualTo(400);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getMessage()).isEqualTo("Id must be null for new entity");
        }
    }

    @SneakyThrows
    @Test
    void createNewEmptyTitleProductTest() {
        emptyProduct = new Product()
                .withPrice(0)
                .withCategoryTitle("Food");

        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(201);
        assert response.body() != null;
        assert productId != null;
        assertThat(response.body().getTitle()).isEqualTo(null);
        assertThat(response.body().getPrice()).isEqualTo(emptyProduct.getPrice());
        assertThat(response.body().getCategoryTitle()).isEqualTo(emptyProduct.getCategoryTitle());
    }

    @SneakyThrows
    @Test
    void createNewEmptyPriceProductTest() {
        emptyProduct = new Product().withTitle(faker.food().ingredient())
                .withCategoryTitle("Food");

        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(201);
        assert response.body() != null;
        assert productId != null;
        assertThat(response.body().getTitle()).isEqualTo(emptyProduct.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(0);
        assertThat(response.body().getCategoryTitle()).isEqualTo(emptyProduct.getCategoryTitle());
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
    void createNewEmptyCategoryProductTest() {
        emptyProduct = new Product().withTitle(faker.food().ingredient())
                .withPrice(100)
                .withCategoryTitle(null);
        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        assertThat(response.code()).isEqualTo(500);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).isEqualTo("Internal Server Error");
        }
    }

    @SneakyThrows
    @Test

    void createNewFullEmptyCProductTest() {
        emptyProduct = new Product().withTitle(null)
                .withPrice(null)
                .withCategoryTitle(null);
        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        assertThat(response.code()).isEqualTo(500);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).isEqualTo("Internal Server Error");
        }
    }

    @SneakyThrows
    @Test
    void createNewProductLongTitleTest() {
        retrofit2.Response<Product> response =
                productService.createProduct(product.withTitle((faker.lorem().fixedString(100))))
                        .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(201);
        assertThat(response.isSuccessful()).isTrue();
        assert response.body() != null;
        assert productId != null;
    }



    @SneakyThrows
    @Test
    void createNewOnlyTitleProductTest() {
        emptyProduct = new Product().withTitle(faker.food().ingredient());
        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        assertThat(response.code()).isEqualTo(500);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).isEqualTo("Internal Server Error");
        }
    }

    @SneakyThrows
    @Test
    void createNewOnlyPriceProductTest() {
        emptyProduct = new Product().withPrice(234);
        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        assertThat(response.code()).isEqualTo(500);
        if (response != null && !response.isSuccessful() && response.errorBody() != null) {
            ResponseBody body = response.errorBody();
            Converter<ResponseBody, ErrorBody> converter = RetrofitUtils.getRetrofit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
            ErrorBody errorBody = converter.convert(body);
            assertThat(errorBody.getError()).isEqualTo("Internal Server Error");
        }
    }
    @SneakyThrows
    @Test
    void createOnlyCategoryProductTest() {
        emptyProduct = new Product()
                .withCategoryTitle("Food");

        retrofit2.Response<Product> response =
                productService.createProduct(emptyProduct)
                        .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(201);
        assert response.body() != null;
        assert productId != null;
        assertThat(response.body().getTitle()).isEqualTo(null);
        assertThat(response.body().getPrice()).isEqualTo(0);
        assertThat(response.body().getCategoryTitle()).isEqualTo(emptyProduct.getCategoryTitle());
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
