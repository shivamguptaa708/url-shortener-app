import { useState } from 'react'
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import LandingPage from './components/LandingPage'
import AboutPage from './components/AboutPage'
import NavBar from './components/NavBar'
import Footer from './components/Footer'
import RegisterPage from './components/RegisterPage'
import { Toaster } from 'react-hot-toast'
import Login from './components/Login'
import DashboardLayout from './components/Dashboard/DashboardLayout'

function App() {
 

  return (
    <>
    {/* //parent and stores all the route component */}
    <BrowserRouter>
    <NavBar/>
    <Toaster position='bottom-center'/>
    <Routes>
    <Route path="/" element={<LandingPage/>} />
    <Route path="/about" element={<AboutPage/>} />
    <Route path="/register" element={<RegisterPage/>} />
    <Route path="/login" element={<Login/>} />
    <Route path="/dashboard" element={<DashboardLayout/>} />
    </Routes>
    <Footer/>
    </BrowserRouter>
    </>  
  )
}

export default App
