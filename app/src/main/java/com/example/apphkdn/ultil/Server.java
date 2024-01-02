package com.example.apphkdn.ultil;

public class Server {
    public static String serverAddress = "http://192.168.2.18/server/"; // Replace with your server address

    public static String linkCategory = serverAddress+"getLsanpham.php";

    public static String linkNewProduct = serverAddress+"getNewProduct.php";

    public static String linkReg = serverAddress+"signup.php";

    public static String linkLog = serverAddress+"login.php";

    public static String linkSendOTP = serverAddress+"sendOTP.php";

    public static String linkLogOTP = serverAddress+"LoginwithOtp.php";

    public static String linkGetProductByCategory = serverAddress+"getProductByCategory.php?idcategory=";

    public static String linkGetProductBySearch = serverAddress+"getProductBySearch.php?key=";

    public static String DetailProduct=serverAddress+"GetProductByID.php?product_id=";

    public static String GetProductByShop=serverAddress+"GetProductByShop.php?id_shop=";

    public static String LinkRegistorSeller = serverAddress+"RegisterSeller.php";

    public static String LinkGetUser = serverAddress+"GetUser.php";

    public static String LinkGetShop = serverAddress+"GetShop.php";

    public static String LinkGetIDCategoryByName = serverAddress+"GetIDCategoryByName.php";

    public static String LinkGetShopByIdUser = serverAddress+"GetShopByIdUser.php";

    public static String AddProduct = serverAddress+"InsertProduct.php";

    public static String UpdateProduct=serverAddress+"UpdateProduct.php";

    public static String LockProduct=serverAddress+"LockProduct.php";
}
