# E-Para Case Study

Bu proje 2 farklı API ile kullanıcının girdiği base ve target currencies datalarına göre exchange rate list datasını h2 veritanına kaydeder. RESTful design prensiplerine göre id veya date range ile sorgulamamızı sağlar.

## Proje Hakkında Notlar

* Projede Maven kullanmayı tercih ettim.

* Kısıtlı zamandan kaynaklı olarak projedeki Entity yapısını basit bir şekilde tutmayı tercih ettim. 
* Fixer API'dan dönen response'a göre gson aracılığıyla Transaction entity'e cast edebiliyoruz. @ElementCollection anotasyonu ile Map<String, Double> tipindeki rates'i veritabanına tutmamız mümkün oluyor. Eğer ki daha büyük ve detaylı bir proje yapacak olsaydım buradaki Rates'i ayırmayı tercih ederdim.
* Veritabanı olarak H2 In memory veritabanını kullandım. Spring Boot sayesinde hızlıca işlemlerimi gerçekleştirebildim. 
* Unit ve Integration testlerimi yazdım. Test senaryolarını daha iyi anlatabilmek ve edge caseleri gözlemleyebilmek için [GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html) stlini kullandım.   Controllerlarda %100 test coverage yakaladım. 
* Anlamlı exceptionlar fırlatabilmek için exception içerisinde duruma göre hatalar oluşturdum. Reel projelerden alışık olduğum biçimiyle olası problemleri takip edebilmek adına bol bol log atmaya özen gösterdim. 
* Transaction Entity'i expose etmemek için DTO'lar oluşturdum, hazır bir mapper kullanmaktansa bu proje özelinde kendi converter'ımı yazmayı tercih ettim. 
* Provider için bir Interface oluşturdum ve olası durumda başka bir exchange rate provider kullanıldığında implementasyonun daha kolay yapılabilmesini tercih ettim.
* Projede 2 adet controller bulunmaktadır; bunlar Exchange ve Transaction olarak ikiye ayrılıyor. İkisi de aynı TransactionService'yi kullanarak insertion veya get işlemlerini yapıyor. Çünkü exchanges API ile provider üstünden data alıp transaction kaydederken, transaction search API ile transaction datalarını get ediyoruz.
* /v1/api/exchanges'e POST işlemi yaptığımızda base ve target currencies ile provider'dan dataları alıp, H2 veritabanına kaydediyoruz. Olası bir error durumunda response olarak bunu dönüyoruz.
* /v1/api/transactions/{id} endpointinde transactionId ile sorgulama yapabiliyoruz. Eğer ki o ID ile yer alan bir transaction varsa kullanıcıya bu datayı gösteriyor, yoksa hata veriyor.
* /v1/api/transactions endpointinde queryParam olarak startDate ve endDate alıyoruz. Bu tarih parametleri "dd/MM/yyyy" formatında olmasına özen gösterdim. Eğer ki bu tarih aralığında belirli transactionlar gerçekleşmişse kullanıcıya liste olarak döndürüyorum. Eğer liste boş ise TransactionListIsEmptyException fırlatıyorum. 

## Kullanım

Projeyi indirdikten sonra application.properties içerisinde yer alan APIKEY datasını giriniz. Aksi halde Fixer Provider API istek atamayacaktır.

```
fixerExchangeProvider.apikey=write your API KEY here!
```

## Swagger

REST API dökümantasyonuna ulaşmak için projeyi çalıştırdıktan sonra Swagger'e gidiniz.

```python
http://localhost:8090/swagger-ui.html
```
