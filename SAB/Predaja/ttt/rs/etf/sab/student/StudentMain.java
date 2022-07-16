package rs.etf.sab.student;

//import rs.etf.sab.operations.*;


import rs.etf.sab.operations.AddressOperations;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.CourierOperations;
import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.DriveOperation;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.StockroomOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;





public class StudentMain {

    public static void main(String[] args) {
     
        AddressOperations addressOperations = new zb180239_AddressOperations(); // Change this to your implementation.
        CityOperations cityOperations = new zb180239_CityOperations(); // Do it for all classes.
        CourierOperations courierOperations = new zb180239_CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new zb180239_CourierRequestOperation();
        DriveOperation driveOperation = new zb180239_DriveOperation();
        GeneralOperations generalOperations = new zb180239_generalOperations();
        PackageOperations packageOperations = new zb180239_PackageOperations();
        StockroomOperations stockroomOperations = new zb180239_StockroomOperations();
        UserOperations userOperations = new zb180239_UserOperations();
        VehicleOperations vehicleOperations = new zb180239_VehicleOperations();

        generalOperations.eraseAll();

        
//

        TestHandler.createInstance(
                addressOperations,
                cityOperations,
                courierOperations,
                courierRequestOperation,
                driveOperation,
                generalOperations,
                packageOperations,
                stockroomOperations,
                userOperations,
                vehicleOperations);

        TestRunner.runTests();

//          ADRESA TEST
//          System.out.println(addressOperations.deleteAddresses("Bliska", 2)); 
//          System.out.println(addressOperations.deleteAdress(13)); 
//          System.out.println(addressOperations.deleteAllAddressesFromCity(2));
//          System.out.println(addressOperations.getAllAddresses()); 
//          System.out.println(addressOperations.getAllAddressesFromCity(2)); 
//          System.out.println(addressOperations.insertAddress("Nova", 1, 2, 3, 3)); 



//          GRAD TEST
//          System.out.println(cityOperations.insertCity("Novapolis", "1111-2222-3333"));
//          System.out.println(cityOperations.deleteCity("Novapolis","Novapolis2")); 
//          System.out.println(cityOperations.deleteCity(7)); 
//          System.out.println(cityOperations.getAllCities()); 


//          USER TEST
//          System.out.println(userOperations.insertUser("testUser2", "Riko", "Martin", "123Ab`bbbbb", 1)); 
//          System.out.println(userOperations.deleteUsers("testUser")); 
//          System.out.println(userOperations.getAllUsers()); 
//          System.out.println(userOperations.getSentPackages("testUser"));
//          System.out.println(userOperations.getSentPackages("testUser1")); 
//          System.out.println(userOperations.declareAdmin("testUser")); 

//          ZAHTEV TEST
//          System.out.println(courierRequestOperation.insertCourierRequest("kori", "1111-2222-3333"));
//          System.out.println(courierRequestOperation.deleteCourierRequest("kori"));
//          System.out.println(courierRequestOperation.getAllCourierRequests());
//          System.out.println(courierRequestOperation.changeDriverLicenceNumberInCourierRequest("kori", "11211"));
//          System.out.println(courierRequestOperation.grantRequest("testUser2"));

//          KURIR TEST
//          System.out.println(courierOperations.getAllCouriers());
//          System.out.println(courierOperations.getCouriersWithStatus(0));
//          System.out.println(courierOperations.getAverageCourierProfit(-1));
//          System.out.println(courierOperations.insertCourier("neradicko", "neradi"));
            
//          LOKACIJA MAGACINA TEST
//          System.out.println(stockroomOperations.insertStockroom(1));
//          System.out.println(stockroomOperations.getAllStockrooms());
//          System.out.println(stockroomOperations.insertStockroom(3));
//          System.out.println(stockroomOperations.deleteStockroomFromCity(1));
//          System.out.println(stockroomOperations.deleteStockroom(1));
          
//          VOZILO TEST
//          System.out.println(vehicleOperations.insertVehicle("DDDD", 0, new BigDecimal(20.2), new BigDecimal(20.2)));
//          System.out.println(vehicleOperations.insertVehicle("DDDD", 0, new BigDecimal(20.2), new BigDecimal(20.2)));
//          System.out.println(vehicleOperations.insertVehicle("DDD1", 0, new BigDecimal(20.2), new BigDecimal(20.2)));
//          System.out.println(vehicleOperations.changeFuelType("DDDD", 1));
//          System.out.println(vehicleOperations.changeCapacity("DDDD", new BigDecimal(20.21)));
//          System.out.println(vehicleOperations.changeConsumption("DDDD", new BigDecimal(20.21)));
//          System.out.println(vehicleOperations.getAllVehichles());
//          System.out.println(vehicleOperations.deleteVehicles("DDDD"));
//          System.out.println(vehicleOperations.getAllVehichles());
//          System.out.println(vehicleOperations.parkVehicle("DDD1", 3));


//            PACKAGE TEST
//            System.out.println(packageOperations.insertPackage(1, 2, "kori", 1, new BigDecimal(20.3)));
//            System.out.println(packageOperations.acceptAnOffer(3));
//            System.out.println(packageOperations.rejectAnOffer(4));
//            System.out.println(packageOperations.getAllPackages());
//            System.out.println(packageOperations.getAllPackagesWithSpecificType(0));
//            System.out.println(packageOperations.getAllUndeliveredPackages());
//            System.out.println(packageOperations.getAllUndeliveredPackagesFromCity(2));
//            System.out.println(packageOperations.changeType(2, 3));
//            System.out.println(packageOperations.changeWeight(2, new BigDecimal(10.2)));
//            System.out.println(packageOperations.getDeliveryStatus(2));
//            System.out.println(packageOperations.getPriceOfDelivery(2));
//            System.out.println(packageOperations.getCurrentLocationOfPackage(2));
//            System.out.println(packageOperations.getAcceptanceTime(2));
    


    }
    
     
    
   
}
