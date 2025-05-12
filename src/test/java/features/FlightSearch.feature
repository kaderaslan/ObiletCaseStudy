Feature: Uçak bileti arama ve seçim işlemleri

  Background:
    Given Kullanıcı Obilet ana sayfasına gider

  Scenario: Uçak bileti arama ve seçim işlemi
    When Kullanıcı "Uçak" sekmesine tıklar
    And Kullanıcı kalkış yeri "izmir" seçer
    And Kullanıcı varış yeri "istanbul" seçer
    And Kullanıcı gidiş tarihini 3, dönüş tarihini 7 gün sonraya ayarlar
    And Kullanıcı "Uçuş Ara" butonuna tıklar
    Then Uçuşlar listelenir
    When Kullanıcı gidiş uçuşunu seçer
    And Gidiş için birden fazla sınıf varsa ilkini seçer
    And Kullanıcı dönüş uçuşunu seçer
    And Dönüş için birden fazla sınıf varsa ilkini seçer
    Then Ödeme sayfası açılır ve seçilen uçuşlar doğrulanır