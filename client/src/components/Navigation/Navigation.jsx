import React from 'react'
import { Wishlist } from '../common/Wishlist'
import { AccountIcon } from '../common/AccountIcon'
import { CartIcon } from '../common/CartIcon'
import { SearchIcon } from '../common/SearchIcon'

const Navigation = () => {
  return (
    <nav className='flex items-center py-6 px-16 justify-between gap-40'>
      {/* Logo */}
      <div className='flex items-center gap-6'>
        <p className='text-3xl text-black font-bold'>Unique Ware</p>
      </div>

      {/* Nav items */}
      <div className='flex flex-wrap items-center gap-10 flex-1'>
        <ul className='flex gap-14 text-gray-400 '>
          <li className='hover:text-black'><a href="/">Shop</a></li>
          <li className='hover:text-black'><a href="/mens">Men</a></li>
          <li className='hover:text-black'><a href="/womens">Women</a></li>
          <li className='hover:text-black'><a href="/kids">Kids</a></li>
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
          <li><a href="/cart-items"><CartIcon /></a></li>
        </ul>
      </div>
    </nav>
  )
}

export default Navigation