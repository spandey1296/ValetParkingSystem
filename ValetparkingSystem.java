package com.company;



import java.util.*;
public class ValetparkingSystem {
    int count=1;   //assign the address as count
    static Map<String,Integer> CarLocationAddress=new HashMap<>();  // location of the car
    static Queue<Integer> smallcar=new LinkedList<>();  // storing the free spaces for small car
    static Queue<Integer> mediumcar=new LinkedList<>();  // storing the free spaces for medium car
    static Queue<Integer> largecar=new LinkedList<>();  // storing the free spaces for large car
    static Map<String,Integer> smallcarformediumpark=new HashMap<>();   // storing the small car into medium garage
    static Map<String,Integer> smallcarforlargepark=new HashMap<>();    //storing the small car into large garage
    static  Map<String,Integer> mediumcarforlargepark=new HashMap<>();   // storing the medium car into large garage

    ValetparkingSystem(int small,int medium,int large)
    {
        for(int i=0;i<small;i++)
        {
            smallcar.add(count++);   //assign free spaces in small garage
        }
        for(int i=0;i<medium;i++)
        {
            mediumcar.add(count++);  //assign free spaces in medium garage
        }
        for(int i=0;i<large;i++)
        {
            largecar.add(count++);    //assign free spaces in large garage
        }
    }
    public static void admitTheCar(String  licensePlatNumber,String carsize)
    {
        if(carsize.equals("small"))
        {
            if(smallcar.size()>0)
            {
                int tempAddress=smallcar.poll();
                CarLocationAddress.put( licensePlatNumber,tempAddress);
            }
            else if(mediumcar.size()>0)
            {
                int tempAddress=mediumcar.poll();
                smallcarformediumpark.put( licensePlatNumber,tempAddress);
                CarLocationAddress.put( licensePlatNumber,Integer.MAX_VALUE);
            }
            else if(largecar.size()>0)
            {
                int tempAddress=largecar.poll();
                smallcarforlargepark.put( licensePlatNumber,tempAddress);
                CarLocationAddress.put( licensePlatNumber,Integer.MIN_VALUE);  // Integer.MIN_VALUE means its store  large  section
            }
            else
            {
                System.out.println("Oh! No accomodation for small car, Try next Time");
            }
        }
        else if(carsize.equals("medium"))
        {
            if(mediumcar.size()>0)
            {
                int tempAddress=mediumcar.poll();
                CarLocationAddress.put( licensePlatNumber,tempAddress);
            }
            else if(smallcar.size()>0 && smallcarformediumpark.size()>0)
            {
                Map.Entry<String,Integer>entry=smallcarformediumpark.entrySet().iterator().next();
                exit(entry.getKey(),"small");
                admitTheCar(entry.getKey(),"small");
                admitTheCar( licensePlatNumber,carsize);
            }
            else if(smallcar.size()>0 && smallcarforlargepark.size()>0)
            {
                Map.Entry<String,Integer>entry=smallcarforlargepark.entrySet().iterator().next();

                exit(entry.getKey(),"small");
                admitTheCar(entry.getKey(),"small");
                admitTheCar( licensePlatNumber,carsize);
            }
            else if(largecar.size()>0)
            {
                int tempAddress=largecar.poll();
                mediumcarforlargepark.put( licensePlatNumber,tempAddress);
                CarLocationAddress.put( licensePlatNumber,Integer.MAX_VALUE-5); // Integer.MAX_VALUE-5 means its store  large  section
            }
            else
            {
                System.out.println("Oh! No accomodation for medium car, Try next Time");
            }
        }
        else
        {
            if(largecar.size()>0)
            {
                int tempAddress=largecar.poll();
                CarLocationAddress.put( licensePlatNumber,tempAddress);
            }
            else if(smallcarforlargepark.size()>0&&smallcar.size()>0)
            {
                Map.Entry<String,Integer>entry=smallcarforlargepark.entrySet().iterator().next();

                exit(entry.getKey(),"small");
                admitTheCar(entry.getKey(),"small");
                admitTheCar( licensePlatNumber,carsize);
            }
            else if(mediumcarforlargepark.size()>0&&mediumcar.size()>0)
            {
                Map.Entry<String,Integer>entry=mediumcarforlargepark.entrySet().iterator().next();
                exit(entry.getKey(),"medium");
                admitTheCar(entry.getKey(),"medium");
                admitTheCar( licensePlatNumber,carsize);
            }
            else if(mediumcarforlargepark.size()>0&&smallcarformediumpark.size()>0&&smallcar.size()>0)
            {

                Map.Entry<String,Integer>entry=smallcarformediumpark.entrySet().iterator().next();
                exit(entry.getKey(),"small");
                admitTheCar(entry.getKey(),"small");

                Map.Entry<String,Integer>entry1=mediumcarforlargepark.entrySet().iterator().next();
                exit(entry.getKey(),"medium");
                admitTheCar(entry.getKey(),"medium");
                admitTheCar( licensePlatNumber,carsize);
            }
            else
            {
                System.out.println("Oh! No accomodation for large car, Try next Time");
            }
        }
    }
    public static void exit(String  licensePlatNumber, String carsize)
    {
        int result=Integer.MAX_VALUE;
        if(CarLocationAddress.containsKey( licensePlatNumber))
        {
            int address=CarLocationAddress.get( licensePlatNumber);
            CarLocationAddress.remove( licensePlatNumber);
            if(address==Integer.MAX_VALUE)   //
            {
                int addressWhereCarStored=smallcarformediumpark.get( licensePlatNumber);
                mediumcar.add(addressWhereCarStored);
                smallcarformediumpark.remove( licensePlatNumber);
            }
            else if(address==Integer.MIN_VALUE)
            {
                int addressWhereCarStored=smallcarforlargepark.get( licensePlatNumber);
                largecar.add(addressWhereCarStored);
                smallcarforlargepark.remove( licensePlatNumber);

            }
            else if(address==Integer.MAX_VALUE-5)
            {
                int addressWhereCarStored=mediumcarforlargepark.get( licensePlatNumber);
                largecar.add(addressWhereCarStored);
                mediumcarforlargepark.remove( licensePlatNumber);
            }
            else
            {
                if(Objects.equals(carsize, "small"))
                {
                    smallcar.add(result);
                }
                else if (carsize.equals("small"))
                {
                    mediumcar.add(result);
                }
                else
                {
                    largecar.add(result);
                }

            }
            System.out.println("car removed ");
        }
        else
        {
            System.out.println("Not Present");
        }
    }
    public static void main (String[] args)
    {

        System.out.println("WELCOME TO VALET PARKING SYSTEM" + new java.util.Date());
        Scanner sc=new Scanner(System.in);
        int smallSpot=sc.nextInt();
        int mediumSpot=sc.nextInt();
        int largeSpot=sc.nextInt();
       new ValetparkingSystem(smallSpot, mediumSpot, largeSpot);
        ArrayList<String> array=new ArrayList<>();
        array.add("skp1 small");
        array.add("skp2 small");
        array.add("skp3 small");
        array.add("skp4 small");
        array.add("skp5 small");
        array.add("skp6 medium");
        for (String s : array) {
            String[] temp = s.split(" ");
            admitTheCar(temp[0], temp[1]);
        }
        exit("Br026","small");

        admitTheCar("bmw","large");

        System.out.println(CarLocationAddress);
    }
}