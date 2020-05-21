from django.shortcuts import render

# Create your views here.
def login(request):
    return render(request,'sessions/login_Register.html')

def logOut(request):
    pass

def home(request):
    return render(request,'index.html')