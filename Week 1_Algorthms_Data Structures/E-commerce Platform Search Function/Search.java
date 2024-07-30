import java.util.Arrays;
public class Search
{
    public static Product linearSearch(Product[] products,String productId)
    {
        for (Product product:products)
        {
            if(product.getProductId().equals(productId))
                return product;
        }
        return null;
    }
    public static Product binarySearch(Product[] products,String productId)
    {
        int left=0;
        int right=products.length-1;
        while(left<=right)
        {
            int mid=left+(right-left)/2;
            int comparison=products[mid].getProductId().compareTo(productId);
            if(comparison==0)
                return products[mid];
            else if(comparison < 0)
                left=mid+1;
            else
                right=mid-1;
        }
        return null;
    }
    public static void main(String[] args)
    {
        Product[] products={new Product("1","Laptop","Electronics"),new Product("2","Smartphone","Electronics"),new Product("3","Shoes","Apparel"),new Product("4","Watch","Accessories"),new Product("5","Bag","Accessories")};
        Arrays.sort(products, (a, b)->a.getProductId().compareTo(b.getProductId()));
        System.out.println("Linear Search:");
        Product result=linearSearch(products,"3");
        System.out.println(result!= null?result:"Product not found");
        System.out.println("Binary Search:");
        result=binarySearch(products,"3");
        System.out.println(result!=null?result:"Product not found");
    }
}