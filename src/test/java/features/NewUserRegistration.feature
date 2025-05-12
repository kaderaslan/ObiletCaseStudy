Feature: New User Registration

  Background:
    Given Kullanıcı Obilet giriş sayfasına gider
    And Kullanıcı "Üye Girişi" sekmesine tıklar
    And Kullanıcı "Üye Ol" butonuna tıklar


  Scenario: Pozitif kayıt işlemi dinamik e-posta ile
    When Kullanıcı e-posta "random" ve şifre "SecurePassword1234" girer
    And Kullanıcı "Üye Ol" butonuna tıklar
    Then Kayıt başarılı olur ve kullanıcı yönlendirilir


  Scenario Outline: Kayıt sırasında boş alan hatası kontrolü
    When Kullanıcı e-posta "<email>" ve şifre "<password>" girer
    And Kullanıcı "Üye Ol" butonuna tıklar
    Then "<errorMessage>" hata mesajı görüntülenir

    Examples:
      | email   | password           | errorMessage                    |
      |         | SecurePassword1234 | E-mail adresi boş bırakılamaz   |
      | random  |                    | Şifre boş bırakılamaz           |