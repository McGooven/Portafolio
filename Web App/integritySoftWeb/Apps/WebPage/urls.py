from django.urls import path
from .views import loginPage,home,formulario,sugerencia,login,perfil

app_name='WebPage'

urlpatterns = [
    path('accounts',loginPage,name='loginPage'),
    path('accounts/login',login, name='login'),
    path('',home, name='inicio'),
    path('form',formulario,name='contacto'),
    path('send',sugerencia,name='enviarSugerencia'),
    path('myAccount',perfil,name='perfil')
]