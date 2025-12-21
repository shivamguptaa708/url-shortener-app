import { useState } from 'react'
import './App.css'
import { BrowserRouter } from 'react-router-dom'
import { getApps } from './utils/helper'

function App() {
 
  const CurrentApp = getApps();

  return (
    <>
    {/* //parent and stores all the route component */}
    <BrowserRouter>
     <CurrentApp/>
    </BrowserRouter>
    </>  
  )
}

export default App
