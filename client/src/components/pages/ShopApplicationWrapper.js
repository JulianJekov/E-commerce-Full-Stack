import React from 'react'
import Navigation from '../Navigation/Navigation'
import { Outlet } from 'react-router-dom'
import Spinner from '../Spinner/Spinner'
import { useSelector } from 'react-redux'

const ShopApplicationWrapper = () => {
  const isLoading = useSelector((state)=> state?.commonState?.loading)
  return (
    <div>
        <Navigation />
        <Outlet />
       {isLoading && <Spinner /> }
    </div>
  )
}

export default ShopApplicationWrapper