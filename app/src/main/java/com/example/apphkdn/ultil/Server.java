package com.example.apphkdn.ultil;

public class Server {
<<<<<<< HEAD
    public static String serverAddress = "http://192.168.2.12"; // Replace with your server address
=======
    public static String serverAddress = "http://192.168.1.25"; // Replace with your server address
>>>>>>> ea5e204054987ed761ba5c543a20d98b45e53826

    public static String linkCategory = serverAddress+"/server/getLsanpham.php";

    public static String linkNewProduct = serverAddress+"/server/getNewProduct.php";

    public static String linkReg = serverAddress+"/server/signup.php";

    public static String linkLog = serverAddress+"/server/login.php";

    public static String linkSendOTP = serverAddress+"/server/sendOTP.php";

    public static String linkLogOTP = serverAddress+"/server/LoginwithOtp.php";

    public static String linkGetProductByCategory = serverAddress+"/server/getProductByCategory.php?idcategory=";

    public static String linkGetProductBySearch = serverAddress+"/server/getProductBySearch.php?key=";

    public static String DetailProduct=serverAddress+"/server/GetProductByID.php?product_id=";

    public static String GetProductByShop=serverAddress+"/server/GetProductByShop.php?id_shop=";

    public static String LinkRegistorSeller = serverAddress+"/server/RegisterSeller.php";

    public static String LinkGetUser = serverAddress+"/server/GetUser.php";

    public static String LinkGetShop = serverAddress+"/server/GetShop.php";

<<<<<<< HEAD
    public static String LinkGetIDCategoryByName = serverAddress+"/server/GetIDCategoryByName.php";
=======
    public static String LinkGetShopByIdUser = serverAddress+"/server/GetShopByIdUser.php";

    public static String AddProduct = serverAddress+"/server/InsertProduct.php";
>>>>>>> ea5e204054987ed761ba5c543a20d98b45e53826
}
