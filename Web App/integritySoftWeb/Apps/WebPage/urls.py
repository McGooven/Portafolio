from django.urls import path
from .views import login,home,formulario

app_name='WebPage'

urlpatterns = [
    path('/login',login,name='login'),
    path('',home, name='inicio'),
    path('/formulario',formulario,name='contacto')
]