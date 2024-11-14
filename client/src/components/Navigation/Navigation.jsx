import React from 'react'
import { Wishlist } from '../common/Wishlist'
import { AccountIcon } from '../common/AccountIcon'
import { CartIcon } from '../common/CartIcon'
import { SearchIcon } from '../common/SearchIcon'
import { Link, NavLink } from 'react-router-dom'
import './Navigation.css';

const Navigation = () => {
  return (
    <nav className='flex items-center py-6 px-16 justify-between gap-20 custom-nav'>
      {/* Logo */}
      <div className='flex items-center gap-6'>
        <a className='text-3xl text-black font-bold gap-8' href='/'>Unique Wear</a>
      </div>

      {/* Nav items */}
      <div className='flex flex-wrap items-center gap-10'>
        <ul className='flex gap-14 text-gray-600 hover:text-black'>
          <li><NavLink to="/" className={({isActive}) => isActive ? 'active-link': ''}>Shop</NavLink></li>
          <li><NavLink to="/men" className={({isActive}) => isActive ? 'active-link': ''}>Men</NavLink></li>
          <li><NavLink to="/women" className={({isActive}) => isActive ? 'active-link': ''}>Women</NavLink></li>
          <li><NavLink to="/kid" className={({isActive}) => isActive ? 'active-link': ''}>Kids</NavLink></li>
        </ul>
      </div>

      {/* Search bar */}
      <div className='flex justify-center'>
        <div className='border rounded flex overflow-hidden'>
          <div className="flex items-center justify-center px-4 border-1">
            <SearchIcon />
            <input type="text" className="px-4 py-2 outline-none" placeholder="Search" />
          </div>

        </div>
      </div>

      {/* Action items - icons */}
      <div className='flex flex-wrap items-center gap-4'>
        <ul className='flex items-center gap-8'>
          <li><button><Wishlist /></button></li>
          <li><button><AccountIcon /></button></li>
          <li><Link to="/cart-items"><CartIcon /></Link></li>
        </ul>
      </div>
    </nav>
  )
}

export default Navigation