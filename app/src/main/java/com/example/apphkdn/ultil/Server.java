package com.example.apphkdn.ultil;

public class Server {
    public static String serverAddress = "http://192.168.2.18/server/"; // Replace with your server address

    public static String linkCategory = serverAddress+"GetCategory.php";

    public static String linkNewProduct = serverAddress+"getNewProduct.php";

    public static String linkReg = serverAddress+"signup.php";

    public static String linkLog = serverAddress+"login.php";

    public static String linkSendOTP = serverAddress+"sendOTP.php";

    public static String linkLogOTP = serverAddress+"LoginwithOtp.php";

    public static String linkGetProductByCategory = serverAddress+"getProductByCategory.php?idcategory=";

    public static String linkGetProductBySearch = serverAddress+"getProductBySearch.php?search=";

    public static String DetailProduct=serverAddress+"GetProductByID.php?product_id=";

    public static String GetProductByShop=serverAddress+"GetProductByShop.php?id_shop=";

    public static String LinkRegistorSeller = serverAddress+"RegisterSeller.php";

    public static String LinkGetUser = serverAddress+"GetUser.php";

    public static String LinkGetShop = serverAddress+"GetShop.php";

    public static String LinkGetShopByIdUser = serverAddress+"GetShopByIdUser.php";

    public static String AddProduct = serverAddress+"InsertProduct.php";

    public static String LinkDataAutotextViewSearch = serverAddress+"getDataAutoTextView.php";

    public static String UpdateProduct=serverAddress+"UpdateProduct.php";

    public static String LockProduct=serverAddress+"LockProduct.php";

    public static String InsertOrder=serverAddress+"InserOrder.php";

    public static String InsertOrderDetail = serverAddress+"InsertOrderDetails.php";

    public static String UpdateOrder = serverAddress+"UpdateOrder.php";

    public static String AcceptOrder=serverAddress+"AcceptOrder.php?";

    public static String GetShopOrder=serverAddress+"GetShopOrder.php?id_shop=";

    public static String GetMyOrder=serverAddress+"GetMyOrder.php?id_user=";
    public static String GetInforProductFirst=serverAddress+"GetProductTopByOrder.php?order_id=";

    public static String GetAllMyOrderApp=serverAddress+"GetAllMyOrder.php?id_user=";

    public static String LinkFilter=serverAddress+"Filter.php";

    public static String WriteRating=serverAddress+"WriteRating.php";

    public static String GetRatingPR=serverAddress+"GetRating.php?id_product=";

    public static String getProductOder=serverAddress+"GetProductOrder.php?id_order=";
}
