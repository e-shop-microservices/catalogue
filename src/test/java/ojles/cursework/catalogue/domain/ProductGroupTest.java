package ojles.cursework.catalogue.domain;

import ojles.cursework.catalogue.exception.ForbiddenActionException;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.ReflectionTestUtils.getField;


public class ProductGroupTest {
    @Test
    public void testDefaultConstructorPresent() {
        assertThat(new ProductGroup(), notNullValue());
    }

    @Test
    public void testParameterizedConstructor1() {
        ProductGroup group = new ProductGroup("random name", "random image path");
        assertThat(group.getId(), equalTo(0L));
        assertThat(group.getName(), equalTo("random name"));
        assertThat(group.getImagePath(), equalTo("random image path"));
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        ProductGroup group = new ProductGroup();
        group.addProduct(product);
        assertThat(group.getProducts(), hasItem(product));
        ProductGroup otherGroup = (ProductGroup) ReflectionTestUtils.getField(product, "group");
        assertThat(otherGroup, equalTo(group));
    }

    @Test(expected = ForbiddenActionException.class)
    public void testShouldFailWhenAddingProductsToGroupWithChildGroups() {
        ProductGroup child = new ProductGroup();
        ProductGroup parent = new ProductGroup();
        parent.addChildGroup(child);
        parent.addProduct(new Product());
    }

    @Test(expected = ForbiddenActionException.class)
    public void testShouldThrowExceptionWhenAddingSelfAsChildGroup() {
        ProductGroup group1 = new ProductGroup();
        group1.addChildGroup(group1);
    }

    @Test
    public void testAddEmptyChildToEmptyParent() {
        ProductGroup child = new ProductGroup();
        ProductGroup parent = new ProductGroup();
        parent.addChildGroup(child);

        // TODO: replace with getters and setters if will be added in the future
        assertThat(getField(child, "parentGroup"), equalTo(parent));
        assertThat((List<ProductGroup>) getField(parent, "children"), hasItems(child));
    }

    @Test
    public void testAddEmptyChildToParentGroupWithProducts() {
        Product product1 = new Product();
        Product product2 = new Product();

        ProductGroup parent = new ProductGroup();
        parent.addProduct(product1);
        parent.addProduct(product2);

        ProductGroup child = new ProductGroup();
        parent.addChildGroup(child);

        assertThat(child.getProducts(), hasItems(product1, product2));
        assertThat(parent.getProducts(), is(empty()));

        // TODO: replace with getters and setters if will be added in the future
        assertThat(getField(child, "parentGroup"), equalTo(parent));
        assertThat((List<ProductGroup>) getField(parent, "children"), hasItems(child));
    }

    @Test
    public void testAddChildGroupFilledWithProductsToEmptyParentGroup() {
        ProductGroup childOfChild1 = new ProductGroup();
        childOfChild1.addProduct(new Product());
        childOfChild1.addProduct(new Product());

        ProductGroup childOfChild2 = new ProductGroup();
        childOfChild2.addProduct(new Product());
        childOfChild2.addProduct(new Product());

        ProductGroup child = new ProductGroup();
        child.addChildGroup(childOfChild1);
        child.addChildGroup(childOfChild2);


        ProductGroup parent = new ProductGroup();
        parent.addChildGroup(child);

        // TODO: replace with getters and setters if will be added in the future
        assertThat(getField(child, "parentGroup"), equalTo(parent));
        assertThat((List<ProductGroup>) getField(parent, "children"), hasItems(child));
    }

    @Test(expected = ForbiddenActionException.class)
    public void testAddChildGroupFilledWithProductsToNonEmptyParentGroup() {
        ProductGroup childOfChild = new ProductGroup();
        childOfChild.addProduct(new Product());
        childOfChild.addProduct(new Product());

        ProductGroup child = new ProductGroup();
        child.addChildGroup(childOfChild);

        ProductGroup parent = new ProductGroup();
        parent.addProduct(new Product());
        parent.addChildGroup(child);

        // TODO: replace with getters and setters if will be added in the future
        assertThat(getField(child, "parentGroup"), equalTo(parent));
        assertThat((List<ProductGroup>) getField(parent, "children"), hasItems(child));
    }
}