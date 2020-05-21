from django.urls import path
from .views import login,home

app_name='WebPage'

urlpatterns = [
    path('',login,name='login'),
    path('/home',home, name='inicio')
]